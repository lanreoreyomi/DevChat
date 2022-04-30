package com.devchats.devchats.interfacce;

import com.devchats.devchats.model.Address;
import java.util.List;

public interface AddressService {

  Address findAddressByUserId(Long id);

  List<Address> getAllAddresses();

  Address save(Address address);
}
