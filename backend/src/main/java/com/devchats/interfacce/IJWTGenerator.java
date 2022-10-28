package com.devchats.interfacce;

import com.devchats.model.AppUser;

import java.util.Map;

public interface IJWTGenerator {

    Map<String, String> generateToken(AppUser user);

}
