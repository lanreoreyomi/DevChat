package com.devchats.service;

import com.devchats.interfacce.IJWTGenerator;
import com.devchats.model.AppUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTService  implements IJWTGenerator {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${app.jwttoken.message}")
    private String message;



    @Override
    public Map<String, String> generateToken(AppUser user) {
        String jwtToken = "";
        jwtToken = Jwts.builder().setSubject(user.getUserName()).setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, "secret").compact();
        Map<String, String> jwtTokenGen = new HashMap<>();
        jwtTokenGen.put("token", jwtToken);
        jwtTokenGen.put("message", message);
        return jwtTokenGen;
    }
}