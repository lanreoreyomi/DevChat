package com.devchats.devchats.interfacce;

import java.text.ParseException;

public interface IUserDetails {

  com.devchats.devchats.model.UserDetails save(com.devchats.devchats.model.UserDetails userDetails)
      throws ParseException;

  com.devchats.devchats.model.UserDetails findUserDetailsByUserId(Long Id);

}
