package com.shoppingapp.shoppingapp.service;

import com.shoppingapp.shoppingapp.dto.request.AddressCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.AddressUpdateRequest;
import com.shoppingapp.shoppingapp.dto.response.AddressResponse;
import com.shoppingapp.shoppingapp.models.Address;

import java.util.List;

public interface AddressService {
    List<Address> getAllAddress();
    AddressResponse getAddressById(long AddressId);
    Address createAddress(AddressCreationRequest request);
    AddressResponse updateAddress(AddressUpdateRequest request, long AddressId);
    String deleteAddress(Long AddressId);
}
