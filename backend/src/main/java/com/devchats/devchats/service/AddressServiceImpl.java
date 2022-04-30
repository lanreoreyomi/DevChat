package com.devchats.devchats.service;

import com.devchats.devchats.interfacce.AddressService;
import com.devchats.devchats.model.Address;
import com.devchats.devchats.repository.AddressRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

  private final AddressRepository addressRepo;

  public AddressServiceImpl(AddressRepository addressRepo) {
    this.addressRepo = addressRepo;
  }

  @Override
  public Address findAddressByUserId(Long id) {
    return addressRepo.findAddressByUserId(id);
  }

  @Override
  public List<Address> getAllAddresses() {
    return addressRepo.findAll();
  }

  @Override
  public Address save(Address address) {
    return addressRepo.save(address);
  }

}
