package com.devchats.JWT;

import java.io.Serializable;

public class JwtResponse implements Serializable {

  private static final long serialVersionUID = -8091879091924046844L;
  private final String jwttoken;
  private final String user;

  public JwtResponse(String jwttoken, String user) {
    this.jwttoken = jwttoken; this.user=user;
  }

  public String getToken() {
    return this.jwttoken;
  }
}