package com.shoppingapp.shoppingapp.service.Impl;

import com.shoppingapp.shoppingapp.dto.request.AddressCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.AddressUpdateRequest;
import com.shoppingapp.shoppingapp.dto.response.AddressResponse;
import com.shoppingapp.shoppingapp.exceptions.AppException;
import com.shoppingapp.shoppingapp.exceptions.ErrorCode;
import com.shoppingapp.shoppingapp.mapper.AddressMapper;
import com.shoppingapp.shoppingapp.models.Address;
import com.shoppingapp.shoppingapp.models.User;
import com.shoppingapp.shoppingapp.repository.AddressRepository;
import com.shoppingapp.shoppingapp.repository.UserRepository;
import com.shoppingapp.shoppingapp.service.AddressService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AddressServiceImp implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public List<AddressResponse> getAllAddress() {
        return addressRepository.findAll().stream().map(addressMapper::toResponse).collect(Collectors.toList());

    }

    @Override
    public AddressResponse getAddressById(long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new AppException(ErrorCode.ADDRESS_NOT_FOUND));
        return addressMapper.toResponse(address);
    }

    @Override
    public AddressResponse createAddress(AddressCreationRequest request) {
        var userOp = userRepository.findById(request.getUser());
        if (userOp.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }

        User user = userOp.get();
        Address address = addressMapper.toAddress(request);
        address.setUser(user);
        return addressMapper.toResponse(addressRepository.save(address));
    }

    @Override
    public String updateAddress(Address request) {
        // Retrieve the existing address
        Address address = addressRepository.findById(request.getAddressID())
                .orElseThrow(() -> new AppException(ErrorCode.ADDRESS_NOT_FOUND));
        address.setCity(request.getCity());
        address.setDistrict(request.getDistrict());
        address.setStreet(request.getStreet());
        address.setSubDistrict(request.getSubDistrict());
        addressRepository.save(address);
        return "Address updated successfully";
    }


    @Override
    public String deleteAddress(Long addressId) {
        addressRepository.deleteById(addressId);
        return "Deleted";
    }


}
