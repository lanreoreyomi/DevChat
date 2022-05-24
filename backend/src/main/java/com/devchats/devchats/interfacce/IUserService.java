package com.devchats.devchats.interfacce;

import com.devchats.devchats.model.User;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public interface IUserService {

  User saveUser(User user);

  List<User> getAllUsers();

  User findUserById(Long userId);

  void deleteUserById(Long userId);

  void findByUsernameOrEmail(User user);

  void updateUserAddress(User user);

 }
