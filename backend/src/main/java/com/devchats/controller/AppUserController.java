package com.devchats.controller;

import com.devchats.dto.AddressDTO;
import com.devchats.dto.UserDTO;
import com.devchats.dto.UserDetailsDTO;
import com.devchats.exceptions.UserNotFoundException;
import com.devchats.model.Address;
import com.devchats.model.AppUser;
import com.devchats.model.Post;
import com.devchats.model.UserDetails;
import com.devchats.service.AddressServiceImpl;
import com.devchats.service.PostServiceImpl;
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
  private final PostServiceImpl postServiceImpl;




  public AppUserController(UserServiceImpl userServiceImpl, AddressServiceImpl addrServiceImpl,
      UserDetailServiceImpl userDetailsImpl,
      PostServiceImpl postServiceImpl) {
    this.userServiceImpl = userServiceImpl;
    this.addrServiceImpl = addrServiceImpl;
    this.userDetailsImpl = userDetailsImpl;
    this.postServiceImpl = postServiceImpl;
  }

  //Get all users
  @GetMapping
  public ResponseEntity<List<UserDTO>> getAllUsers() {

    List<UserDTO> userDtoList = new ArrayList<>();
    for (AppUser us : userServiceImpl.getAllUsers()) {
      if (us != null) {
        Address addressByUserId = addrServiceImpl.findAddressByUserId(us.getUserId());
        us.setAddress(addressByUserId);

        userDtoList.add(convertUserEntityToDTO(us));
      }
    }
    return ResponseEntity.ok(userDtoList);
  }


  // Creates a user
  @PostMapping("/register")
  public ResponseEntity<UserDTO> createUser(@RequestBody AppUser request)  //request body  is User
      throws UserNotFoundException {

    AppUser user = userServiceImpl.saveUser(request);

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(convertUserEntityToDTO(user));
  }

  // Gets a user by Id
  @GetMapping("/{id}")  //http://localhost:5050/api/v1/user/1
  public ResponseEntity<UserDTO> getUserById(@PathVariable String id) throws UserNotFoundException {

    return ResponseEntity.status(HttpStatus.OK)      //return userDTO
        .body(convertUserEntityToDTO(userServiceImpl.findUserById(Long.valueOf(id))));
  }


  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteUserById(@PathVariable String id) //returns string
      throws UserNotFoundException {

    userServiceImpl.deleteUserById(Long.valueOf(id)); //delete user

    return ResponseEntity.ok("User Successfully deleted"); //return string
  }



  @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")

  public ResponseEntity<UserDTO> updateUserById(@PathVariable String id, @RequestBody AppUser request)
      throws UserNotFoundException {

    if (id != null) {
      request.setUserId(Long.valueOf(id));
    }

    return ResponseEntity.ok(convertUserEntityToDTO(userServiceImpl.saveUser(request)));
  }

  //update user details
  @PutMapping("/{id}/userdetails")

  public ResponseEntity<UserDetailsDTO> updateUserDetails(@PathVariable String id,
      @RequestBody UserDetails request) throws UserNotFoundException {

    AppUser user = userServiceImpl.findUserById(Long.valueOf(id));
    if (user != null
        && user.getUserdetails() != null) {
      request.setId(user.getUserdetails().getId());
    }
    request.setUser(user);
    return ResponseEntity.ok(convertUserDetailsEntityToDTO(userDetailsImpl.save(request)));
  }

  //update address
  @PutMapping("/{id}/address")
  public ResponseEntity<AddressDTO> updateUserAddress(@PathVariable String id,
      @RequestBody Address request) throws UserNotFoundException {

    AppUser user = userServiceImpl.findUserById(Long.valueOf(id));

    if (user != null
        && user.getAddress() != null) {
      request.setAddressId(user.getAddress().getAddressId());
    }

    request.setUser(user);
    return ResponseEntity.ok(convertAddressEntityToDTO(addrServiceImpl.save(request)));
  }

  @GetMapping("/{username}/posts")
  public ResponseEntity<List<Post>> getAllPosts(@PathVariable String username){

    return ResponseEntity.ok(postServiceImpl.getPostsByUsername(username));
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

    if ( userDetails != null ) {

      userDetailsDTO = new UserDetailsDTO();
      BeanUtils.copyProperties(userDetails, userDetailsDTO);

    }

    return userDetailsDTO;
  }
}
