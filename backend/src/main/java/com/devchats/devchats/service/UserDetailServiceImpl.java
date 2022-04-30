package com.devchats.devchats.service;

import com.devchats.devchats.interfacce.UserDetails;
import com.devchats.devchats.repository.UserDetailsRepository;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetails {

  private final UserDetailsRepository userDetailsRepo;

  public UserDetailServiceImpl(UserDetailsRepository userDetailsRepo) {
    this.userDetailsRepo = userDetailsRepo;
  }

  @Override
  public com.devchats.devchats.model.UserDetails save(
      com.devchats.devchats.model.UserDetails userDetails) {
    return userDetailsRepo.save(userDetails);
  }

  @Override
  public com.devchats.devchats.model.UserDetails findUserDetailsByUserId(Long id) {
    return userDetailsRepo.findUserDetailsByUserId(id);
  }
}
