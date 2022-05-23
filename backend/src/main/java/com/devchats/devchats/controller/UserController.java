package com.devchats.devchats.controller;

import com.devchats.devchats.dto.AddressDTO;
import com.devchats.devchats.dto.UserDTO;
import com.devchats.devchats.dto.UserDetailsDTO;
import com.devchats.devchats.exceptions.UserNotFoundException;
import com.devchats.devchats.model.Address;
import com.devchats.devchats.model.User;
import com.devchats.devchats.model.UserDetails;
import com.devchats.devchats.service.AddressServiceImpl;
import com.devchats.devchats.service.UserDetailServiceImpl;
import com.devchats.devchats.service.UserServiceImpl;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/user")
@Slf4j
public class UserController {

  private final UserServiceImpl userServiceImpl;
  private final AddressServiceImpl addrServiceImpl;
  private final UserDetailServiceImpl userDetailsImpl;

  public UserController(UserServiceImpl userServiceImpl, AddressServiceImpl addrServiceImpl,
      UserDetailServiceImpl userDetailsImpl) {
    this.userServiceImpl = userServiceImpl;
    this.addrServiceImpl = addrServiceImpl;
    this.userDetailsImpl = userDetailsImpl;
  }


  //Get all users
  @GetMapping //http://localhost:5050/api/v1/user
  public ResponseEntity<List<UserDTO>> getAllUsers() {

    List<UserDTO> userDtoList = new ArrayList<>();  //create a list of userDTO
    for (User us : userServiceImpl.getAllUsers()) {
      //convert each user to userDTO
      if (us != null) { //if user is not null

        Address addressByUserId = addrServiceImpl.findAddressByUserId(us.getId());
        us.setAddress(addressByUserId);

        System.out.println("us = " + us);

        userDtoList.add(convertUserEntityToDTO(us)); //add userDTO to list
      }
    }
    return ResponseEntity.ok(userDtoList);//return list of userDTO
  }   //end of getAllUsers


  // Creates a user
  @PostMapping(consumes = "application/json")   //http://localhost:5050/api/v1/user
  public ResponseEntity<UserDTO> createUser(@RequestBody User request)  //request body  is User
      throws UserNotFoundException {

    User user = userServiceImpl.saveUser(request);      //save user

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(convertUserEntityToDTO(user));      //return userDTO
  }     //end of createUser

  // Gets a user by Id
  @GetMapping("/{id}")  //http://localhost:5050/api/v1/user/1
  public ResponseEntity<UserDTO> getUserById(@PathVariable String id) throws UserNotFoundException {

    return ResponseEntity.status(HttpStatus.OK)      //return userDTO
        .body(convertUserEntityToDTO(userServiceImpl.findUserById(Long.valueOf(id))));
  }       //end of getUserById


  // Deletes a user by Id
  @DeleteMapping("/{id}")     //http://localhost:5050/api/v1/user/1
  public ResponseEntity<String> deleteUserById(@PathVariable String id) //returns string
      throws UserNotFoundException {

    userServiceImpl.deleteUserById(Long.valueOf(id)); //delete user

    return ResponseEntity.ok("User Successfully deleted"); //return string
  }//end of deleteUserById


   // Updates a user by Id
  @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
  //http://localhost:5050/api/v1/user/1
  public ResponseEntity<UserDTO> updateUserById(@PathVariable String id, @RequestBody User request)
      throws UserNotFoundException {

    if (id != null || !id.isEmpty()) {

      request.setId(Long.valueOf(id));

    }

    User user = userServiceImpl.saveUser(request); //find user by id
    return ResponseEntity.ok(convertUserEntityToDTO(user));
    //end of updateUserById
  }

  //update user details
  @PutMapping("/{id}/userdetails")
  //http://localhost:5050/api/v1/user/1/userdetails
  public ResponseEntity<UserDetailsDTO> updateUserDetails(@PathVariable String id,
      @RequestBody UserDetails request) throws UserNotFoundException, ParseException {

    User user = userServiceImpl.findUserById(Long.valueOf(id)); //find user by id

    if (user != null
        && user.getUserdetails() != null) { //if user is not null != null) { //if user is not null
      request.setId(user.getUserdetails().getId());
    }
    request.setUser(user);
    UserDetails userDetails = userDetailsImpl.save(request);//save user details

    return ResponseEntity.ok(convertUserDetailsEntityToDTO(userDetails)); //return user details
  }

  //update address
  @PutMapping("/{id}/address")  //http://localhost:5050/api/v1/user/1/address
  public ResponseEntity<AddressDTO> updateUserAddress(@PathVariable String id,
      @RequestBody Address request) throws UserNotFoundException {

    User user = userServiceImpl.findUserById(Long.valueOf(id)); //find user by id

    if (user != null
        && user.getAddress() != null) { //if user is not null != null) { //if user is not null
      request.setId(user.getAddress().getId());
    }
    request.setUser(user);
    Address address = addrServiceImpl.save(request);//save user details
    return ResponseEntity.ok(convertAddressEntityToDTO(address)); //return user details
  }


  public static UserDTO convertUserEntityToDTO(User user) {



    UserDTO userDTO = new UserDTO();
    BeanUtils.copyProperties(user, userDTO);

    if (user.getAddress() != null) {
      userDTO.setAddress(convertAddressEntityToDTO(user.getAddress()));
    }
    if (user.getUserdetails() != null) {

      userDTO.setUserDetails(convertUserDetailsEntityToDTO(user.getUserdetails()));
    }

    return userDTO;
  }

  public static AddressDTO convertAddressEntityToDTO(Address address) {

    AddressDTO addressDTO = null;

    if (address != null) {

      addressDTO = new AddressDTO();
      BeanUtils.copyProperties(address, addressDTO);

    }
    return addressDTO;
  }

  public static UserDetailsDTO convertUserDetailsEntityToDTO(UserDetails userDetails) {

    UserDetailsDTO userDetailsDTO = null;

    if (userDetails != null) {

      userDetailsDTO = new UserDetailsDTO();
      BeanUtils.copyProperties(userDetails, userDetailsDTO);

    }

    return userDetailsDTO;
  }
}
