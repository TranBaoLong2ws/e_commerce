package com.TranBaoLong2ws.springboot.E_commerce.Service;

import com.TranBaoLong2ws.springboot.E_commerce.Entity.Role;
import com.TranBaoLong2ws.springboot.E_commerce.Entity.User;
import com.TranBaoLong2ws.springboot.E_commerce.Reponse.AuthenticationReponse;
import com.TranBaoLong2ws.springboot.E_commerce.Repository.RoleRepository;
import com.TranBaoLong2ws.springboot.E_commerce.Repository.UserRepository;
import com.TranBaoLong2ws.springboot.E_commerce.Request.LoginRequest;
import com.TranBaoLong2ws.springboot.E_commerce.Request.RegisterRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class UserServiceImpl implements  UserService{
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

   private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public void registerUser(RegisterRequest registerRequest) {

      if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()){
          throw new RuntimeException("Email already exists");
      }

        User user = new User();
        user.setId(0);
        user.setFullname(registerRequest.getFullname());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.setAddress(registerRequest.getAddress());
        user.setPhone(registerRequest.getPhone());
        user.setRoles(initAuthority());

        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthenticationReponse loginUser(LoginRequest loginRequest) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        String token = jwtService.generateToken(new HashMap<>(), user);

        System.out.println(user.getAuthorities());

        return  new AuthenticationReponse(token);
    }

    private List<Role> initAuthority() {
        boolean isFirst = userRepository.count() == 0;

        List<Role> roles = new ArrayList<>();

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("USER role not found"));

        roles.add(userRole);

        if (isFirst) {
            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseThrow(() -> new RuntimeException("ADMIN role not found"));
            roles.add(adminRole);
        }

        return roles;
    }
}
