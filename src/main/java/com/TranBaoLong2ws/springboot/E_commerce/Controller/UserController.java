package com.TranBaoLong2ws.springboot.E_commerce.Controller;

import com.TranBaoLong2ws.springboot.E_commerce.Reponse.AuthenticationReponse;
import com.TranBaoLong2ws.springboot.E_commerce.Request.LoginRequest;
import com.TranBaoLong2ws.springboot.E_commerce.Request.RegisterRequest;
import com.TranBaoLong2ws.springboot.E_commerce.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Register user", description = "Submit information user")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/register")
    public String register(@Valid  @RequestBody RegisterRequest registerRequest){
        userService.registerUser(registerRequest);
        return "success";
    }


    @Operation(summary = "Login a user", description = "Submit email && password to authenticate user")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public AuthenticationReponse login (@RequestBody LoginRequest loginRequest){
        return  userService.loginUser(loginRequest);
    }


}
