package com.devchats.ServiceInterface;

import com.devchats.model.AppUser;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public interface UserService {

  AppUser saveUser(AppUser user);

  List<AppUser> getAllUsers();

  AppUser findUserById(Long userId);

  AppUser findByUserName(String  username);

  void deleteUserById(Long userId);

  void checkIfUserExist(AppUser user);

  void updateUserAddress(AppUser user);


}
