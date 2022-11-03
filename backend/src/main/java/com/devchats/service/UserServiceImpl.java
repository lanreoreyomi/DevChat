package com.devchats.service;

import com.devchats.exceptions.CustomException;
import com.devchats.exceptions.UserNotFoundException;
import com.devchats.interfacce.UserService;
import com.devchats.model.AppUser;
import com.devchats.repository.UserRepository;
import com.devchats.util.BycryptEncoder;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class UserServiceImpl implements UserService {


  private final UserRepository userRepo;

  private BycryptEncoder encoder;


  public UserServiceImpl(UserRepository userRepo,
      BycryptEncoder encoder) {
    this.userRepo = userRepo;
    this.encoder = encoder;
  }

  @Transactional
  public AppUser saveUser(AppUser user) throws UserNotFoundException {

    checkIfUserExist(user);

    try {
      user.setPassword(encoder.passwordEncoder().encode(user.getPassword()));
      log.info("Saving user" + user);
      return userRepo.save(user);
    } catch (DataIntegrityViolationException ex) {
      throw new CustomException("Error occurred while saving user");
    }

  }


  public List<AppUser> getAllUsers() {
    log.info("Retrieving all users");
    return userRepo.findAll();
  }

  public AppUser findUserById(Long userId) throws UserNotFoundException {
    log.info("Retrieving user with id: " + userId);
    return userRepo.findById(userId)
        .orElseThrow(
            () -> new UserNotFoundException(String.format("User with id %s not found", userId)));
  }

  @Override
  public AppUser findByUserName(String username) {

    Optional<AppUser> byUserName = userRepo.findByUserName(username);
    if (!byUserName.isPresent()) {
      throw new UserNotFoundException(String.format("User with username: %s not found", username));
    }
    return byUserName.get();
  }

  public void deleteUserById(Long userId) throws UserNotFoundException {
    AppUser user = findUserById(userId);
    if (user != null) {
      log.info("Deleting user with id: " + userId);
      userRepo.delete(user);
    } else {
      throw new UserNotFoundException(String.format("User with %s not found", userId));
    }
  }

  @Override
  public void checkIfUserExist(AppUser user) {
    AppUser byEmail = userRepo.findByUserNameOrEmail(user.getUserName(), user.getEmail());
    if (byEmail != null) {

      throw new UserNotFoundException(
          String.format("Email: %s or Username: %s already taken", user.getEmail(),
              user.getUserName()));
    }
  }

  @Override
  public void updateUserAddress(AppUser user) {
    log.info("Updating user address");
    AppUser byEmail = userRepo.findByUserNameOrEmail(user.getUserName(), user.getEmail());
    if (byEmail != null) {
      throw new UserNotFoundException(String.format("%s not already taken", user.getEmail()));
    }
  }

  public void deleteAllUsers() {
    userRepo.deleteAll();
  }
}