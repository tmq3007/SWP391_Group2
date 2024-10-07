package com.shoppingapp.shoppingapp.service;

import com.shoppingapp.shoppingapp.models.Address;

import java.util.List;

public interface AddressService {
    List<Address> getAllAddresses();
    Address getAddressById(long AddressID);
    Address addAddress(Address address);
    Address updateAddress(Address address, long AddressID);
    String deleteAddress(long addressID);
}
