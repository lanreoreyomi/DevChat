package com.devchats.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedUser {


  private static Authentication authentication=null;

  public static Authentication getInstance(){
    if(authentication==null){
      authentication = SecurityContextHolder.getContext().getAuthentication();
    }
    return  authentication;
  }

 }
