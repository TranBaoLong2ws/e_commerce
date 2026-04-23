package com.TranBaoLong2ws.springboot.E_commerce.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;


@Service
public class JwtServiceImpl implements  JwtService{

    ///  lấy dữ liê từ file application l khóa để ký JWT
    @Value("${spring.jwt.secret}")
    private String SECRET_KEY;

    ///  lấy thời gian đăng ký cua JWT
    @Value("${spring.jwt.expiration}")
    private long JWT_EXPIRATION;


    @Override
    public String extractUsername(String token) {
        return extractClaims(token,Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsResolver){
        final  Claims claims =extractAllClaims(token); /// xác thực và lấy toàn bộ Claims
        return claimsResolver.apply(claims);/// Thực hiện function bạn truyền và
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser()///  đọc và xử lý token
                .verifyWith(getSigningKey())/// kiểm tra chữ kyss
                .build()/// Build parser hoàn chỉnh
                .parseSignedClaims(token)/// kieemr tra token và kiểm tra chữ ký, kiểm tra format
                .getPayload(); /// lấy phần PayLoad
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUsername(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpiration(token));
    }

    private boolean isTokenExpiration(String token){
        return extractExpiration(token).before(new Date());

    }

    private Date extractExpiration(String token){
        return  extractClaims(token, Claims::getExpiration);

    }

    @Override
    public String generateToken(Map<String, Object> claims , UserDetails userDetails) {
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(getSigningKey(), Jwts.SIG.HS256)
                .compact();

    }

    private SecretKey getSigningKey() {
       byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY); /// SECRET_KEY đang có dạng Base64 cần chuyển về byte để sử dụng
       return Keys.hmacShaKeyFor(keyBytes); /// trả về key dạng byte
    }
}
