package com.devchats.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString
public class UserDTO {

  private Long userId;
  private String firstName;
  private String userName;
  private String lastName;
  private String email;
  private AddressDTO address;
  private UserDetailsDTO userDetails;


}
