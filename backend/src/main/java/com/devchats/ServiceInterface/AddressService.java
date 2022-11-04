package com.devchats.ServiceInterface;

import com.devchats.model.Address;

import java.util.List;

public interface AddressService {

  Address findAddressByUserId(Long id);

  List<Address> getAllAddresses();

  Address save(Address address);
}
