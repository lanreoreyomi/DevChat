package com.devchats.security;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsService {

    UserDetails findUserByUsername (String username);
}

