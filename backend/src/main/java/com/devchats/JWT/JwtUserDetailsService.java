package com.devchats.JWT;


import com.devchats.model.AppUser;
import com.devchats.service.UserServiceImpl;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

  @Autowired
  private UserServiceImpl userService;


  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    AppUser user = getUserByName(username);
    if (user != null) {
      return new User(user.getUserName(), user.getPassword(),
          new ArrayList<>());
    } else {
      throw new UsernameNotFoundException("User not found with username: " + username);
    }
  }

  private AppUser getUserByName(String userName) {

    return userService.findByUserName(userName);
  }
}