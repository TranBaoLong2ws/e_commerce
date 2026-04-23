package com.TranBaoLong2ws.springboot.E_commerce.Utils;

import com.TranBaoLong2ws.springboot.E_commerce.Entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import org.springframework.security.access.AccessDeniedException;

@Component
public class FinAuthenticatedUserImpl implements FinAuthenticatedUser{
    @Override
    public User getAuthenticatedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()
                || authentication.getPrincipal().equals("anonymousUser")){
            throw new AccessDeniedException("Authentication requied");
        }
        return  (User) authentication.getPrincipal();
    }
}
