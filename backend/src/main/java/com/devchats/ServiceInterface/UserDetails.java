package com.devchats.ServiceInterface;

public interface UserDetails {

  com.devchats.model.UserDetails save(
      com.devchats.model.UserDetails userDetails);

  com.devchats.model.UserDetails findUserDetailsByUserId(Long Id);

}
