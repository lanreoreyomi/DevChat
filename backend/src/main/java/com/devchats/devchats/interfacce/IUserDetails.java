package com.devchats.devchats.interfacce;

public interface UserDetails {

  com.devchats.devchats.model.UserDetails save(com.devchats.devchats.model.UserDetails userDetails);

  com.devchats.devchats.model.UserDetails findUserDetailsByUserId(Long Id);

}
