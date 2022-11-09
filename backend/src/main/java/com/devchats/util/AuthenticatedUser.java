package com.devchats.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedUser {


    public static Authentication getAuthenticatedUser() {
        return SecurityContextHolder.getContext().getAuthentication();

    }

}
