package com.devchats.repository;

import com.devchats.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {

  AppUser findByUserNameOrEmail(String username, String email);


  Optional<AppUser> findByUserName(String username);
  Optional<AppUser> findByUserNameAndPassword(String username, String password);

}
