package com.TranBaoLong2ws.springboot.E_commerce.Config;

import com.TranBaoLong2ws.springboot.E_commerce.Service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService,@Lazy UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {



            final String authHeader = request.getHeader("Authorization"); /// lấy header Authorization
            final String jwt ;
            final String userEmail;

            if (authHeader == null || !authHeader.startsWith("Bearer ")){ ///  kiểm tra nếu không Bearer hoặc không đúng from thì bỏ qua
                filterChain.doFilter(request,response);
                return;
            }

            try {
                ///  cắt bỏ "Bearer chỉ ấy token"
                jwt = authHeader.substring(7);

                ///  lấy userEmail từ token
                userEmail = jwtService.extractUsername(jwt);

                /// kiểm tra user đã đăng nhập chưa có user trong token không, chưa có authentication trong context
                if (userEmail != null  && SecurityContextHolder.getContext().getAuthentication() == null){
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail); /// load từ DB lên

                    if (jwtService.isTokenValid(jwt, userDetails)){ /// kiểm tra token
                    ///  tạo object đại diện cho user đã đăng nhập
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());

                        ///  gắn thông tin request thêm id, session ìno
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        ///  đánh dấu user đã đăng nhpapj thành công
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }

                }

                ///  cho request đi tiếp
                filterChain.doFilter(request,response);

            } catch (io.jsonwebtoken.JwtException e){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"Token không hợp lệ hoặc đã hết hạn\"}");
            }
        }
}
