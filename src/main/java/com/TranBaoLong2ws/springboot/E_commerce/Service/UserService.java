package com.TranBaoLong2ws.springboot.E_commerce.Service;

import com.TranBaoLong2ws.springboot.E_commerce.Reponse.AuthenticationReponse;
import com.TranBaoLong2ws.springboot.E_commerce.Request.LoginRequest;
import com.TranBaoLong2ws.springboot.E_commerce.Request.RegisterRequest;
import org.springframework.stereotype.Service;


public interface UserService {

    public void registerUser(RegisterRequest registerRequest);

    public AuthenticationReponse loginUser(LoginRequest loginRequest);

}
