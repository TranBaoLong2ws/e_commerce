package com.TranBaoLong2ws.springboot.E_commerce.Utils;

import com.TranBaoLong2ws.springboot.E_commerce.Entity.User;

import java.nio.file.AccessDeniedException;

public interface FinAuthenticatedUser {
    User getAuthenticatedUser();
}
