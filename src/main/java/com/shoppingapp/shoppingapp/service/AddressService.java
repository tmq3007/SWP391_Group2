package com.shoppingapp.shoppingapp.service;

import com.shoppingapp.shoppingapp.dto.request.AddressCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.AddressUpdateRequest;
import com.shoppingapp.shoppingapp.dto.response.AddressResponse;
import com.shoppingapp.shoppingapp.models.Address;

import java.util.List;

public interface AddressService {
    List<AddressResponse> getAllAddress();
    AddressResponse getAddressById(long AddressId);
    AddressResponse createAddress(AddressCreationRequest request);
    String updateAddress(Address address);
    String deleteAddress(Long AddressId);

}
