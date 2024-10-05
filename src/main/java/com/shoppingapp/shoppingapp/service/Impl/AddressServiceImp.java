package com.shoppingapp.shoppingapp.service.Impl;


import com.shoppingapp.shoppingapp.models.Address;
import com.shoppingapp.shoppingapp.repository.AddressRepository;
import com.shoppingapp.shoppingapp.service.AddressService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AddressServiceImp implements AddressService {
    @Autowired
    private AddressRepository addressRepository;
    @Override

    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    @Override
    public Address getAddressById(long AddressID) {
        return  addressRepository.findById(AddressID)
                .orElseThrow(() -> new RuntimeException("Address not found"));
    }

    @Override
    public Address addAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Address updateAddress(Address addressDetails, long AddressID) {
        Address address = addressRepository.findById(AddressID)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        address.setCity(addressDetails.getCity());
        address.setDistrict(addressDetails.getDistrict());
        address.setSubDistrict(addressDetails.getSubDistrict());
        address.setStreet(addressDetails.getStreet());

        return addressRepository.save(address);
    }

    @Override
    public String deleteAddress(long addressID) {
        addressRepository.deleteById(addressID);
        return "deleted";
    }
}
