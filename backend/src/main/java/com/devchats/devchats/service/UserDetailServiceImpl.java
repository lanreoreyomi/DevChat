package com.devchats.devchats.service;

import static com.devchats.devchats.util.RegextUtils.dateIsValid;
import static com.devchats.devchats.util.RegextUtils.parserDateOfBirth;

import com.devchats.devchats.exceptions.DateException;
import com.devchats.devchats.interfacce.IUserDetails;
import com.devchats.devchats.model.UserDetails;
import com.devchats.devchats.repository.UserDetailsRepository;
import java.text.ParseException;
import java.time.LocalDate;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements IUserDetails {


  private final UserDetailsRepository userDetailsRepo;

  public UserDetailServiceImpl(UserDetailsRepository userDetailsRepo) {
    this.userDetailsRepo = userDetailsRepo;
  }

  @Override
  public UserDetails save(
      UserDetails userDetails) throws ParseException {

    UserDetails details = null;


      if (dateIsValid(userDetails.getDob())) {

        userDetails.setDateOfBirth(parserDateOfBirth(userDetails.getDob()));

        details = userDetailsRepo.save(userDetails);
      } else {

        throw new DateException("Please Enter valid date format: YYYY-MM-DD");

      }


    return details;
  }

  @Override
  public UserDetails findUserDetailsByUserId(Long id) {
    return userDetailsRepo.findUserDetailsByUserId(id);
  }
}
