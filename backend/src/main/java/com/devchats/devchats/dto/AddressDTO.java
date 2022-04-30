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
public class AddressDTO {

  private Long id;
  private String addressLine1;
  private String city;
  private String state;
  private String country;
  private String zipcode;


}
