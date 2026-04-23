package com.TranBaoLong2ws.springboot.E_commerce.Reponse;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationReponse {

    private String token;

    public AuthenticationReponse(String token) {
        this.token = token;
    }
}
