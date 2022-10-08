package com.devchats.interfacce;

import com.devchats.model.AppUser;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public interface UserService {

  AppUser saveUser(AppUser user);

  List<AppUser> getAllUsers();

  AppUser findUserById(Long userId);

  void deleteUserById(Long userId);

  void findByUserNameOrEmail(AppUser user);

  void updateUserAddress(AppUser user);

 }
