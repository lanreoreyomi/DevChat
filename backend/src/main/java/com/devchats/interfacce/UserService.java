package com.devchats.interfacce;

import com.devchats.exceptions.UserNotFoundException;
import com.devchats.model.AppUser;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public interface UserService {

  AppUser saveUser(AppUser user);

  List<AppUser> getAllUsers();

  AppUser findUserById(Long userId);

  AppUser findByUserName(String  username);

  void deleteUserById(Long userId);

  void findByUserNameOrEmail(AppUser user);

  void updateUserAddress(AppUser user);
  public AppUser getUserByNameAndPassword(String name, String password) throws UserNotFoundException;


}
