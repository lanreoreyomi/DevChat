package com.devchats.controller;

import com.devchats.JWT.JwtAuthenticationController;
import com.devchats.dto.AddressDTO;
import com.devchats.dto.UserDTO;
import com.devchats.dto.UserDetailsDTO;
import com.devchats.exceptions.UserNotFoundException;
import com.devchats.model.Address;
import com.devchats.model.AppUser;
import com.devchats.model.UserDetails;
import com.devchats.service.AddressServiceImpl;
import com.devchats.service.UserDetailServiceImpl;
import com.devchats.service.UserServiceImpl;
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
public class AppUserController {

  private final UserServiceImpl userServiceImpl;
  private final AddressServiceImpl addrServiceImpl;
  private final UserDetailServiceImpl userDetailsImpl;




  public AppUserController(UserServiceImpl userServiceImpl, AddressServiceImpl addrServiceImpl,
                           UserDetailServiceImpl userDetailsImpl,
      JwtAuthenticationController jwtAuthenticationController) {
    this.userServiceImpl = userServiceImpl;
    this.addrServiceImpl = addrServiceImpl;
    this.userDetailsImpl = userDetailsImpl;
    }

  //Get all users
  @GetMapping //http://localhost:5050/api/v1/user
  public ResponseEntity<List<UserDTO>> getAllUsers() {

    List<UserDTO> userDtoList = new ArrayList<>();
    for (AppUser us : userServiceImpl.getAllUsers()) {
      //convert each user to userDTO
      if (us != null) { //if user is not null

        Address addressByUserId = addrServiceImpl.findAddressByUserId(us.getUserId());
        us.setAddress(addressByUserId);

        userDtoList.add(convertUserEntityToDTO(us));
      }
    }
    return ResponseEntity.ok(userDtoList);
  }


  // Creates a user
//  @PostMapping(consumes = "application/json")   //http://localhost:5050/api/v1/register
  @PostMapping("/register")   //http://localhost:5050/api/v1/register
  public ResponseEntity<Long> createUser(@RequestBody AppUser request)  //request body  is User
      throws UserNotFoundException {

    AppUser user = userServiceImpl.saveUser(request);      //save user

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(user.getUserId());      //return userDTO
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
  public ResponseEntity<UserDTO> updateUserById(@PathVariable String id, @RequestBody AppUser request)
      throws UserNotFoundException {

    if (id != null || !id.isEmpty()) {

      request.setUserId(Long.valueOf(id));

    }

     return ResponseEntity.ok(convertUserEntityToDTO(userServiceImpl.saveUser(request)));
    //end of updateUserById
  }

  //update user details
  @PutMapping("/{id}/userdetails")
  //http://localhost:5050/api/v1/user/1/userdetails
  public ResponseEntity<UserDetailsDTO> updateUserDetails(@PathVariable String id,
                                                          @RequestBody UserDetails request) throws UserNotFoundException {

    AppUser user = userServiceImpl.findUserById(Long.valueOf(id)); //find user by id

    if (user != null
        && user.getUserdetails() != null) { //if user is not null != null) { //if user is not null
      request.setId(user.getUserdetails().getId());
    }
    request.setUser(user);
    return ResponseEntity.ok(convertUserDetailsEntityToDTO(userDetailsImpl.save(request))); //return user details
  }

  //update address
  @PutMapping("/{id}/address")  //http://localhost:5050/api/v1/user/1/address
  public ResponseEntity<AddressDTO> updateUserAddress(@PathVariable String id,
                                                      @RequestBody Address request) throws UserNotFoundException {

    AppUser user = userServiceImpl.findUserById(Long.valueOf(id)); //find user by id

    if (user != null
        && user.getAddress() != null) { //if user is not null != null) { //if user is not null
      request.setAddressId(user.getAddress().getAddressId());
    }

    request.setUser(user);
     return ResponseEntity.ok(convertAddressEntityToDTO(addrServiceImpl.save(request))); //return user details
  }


  public static UserDTO convertUserEntityToDTO(AppUser user) {

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
