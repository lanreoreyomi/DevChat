package com.devchats.devchats.dto;


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
public class UserDetailsDTO {

  private Long id;
  private String phone;
  private String occupation;
  private String education;
  private String dob;
  private String about;

}
