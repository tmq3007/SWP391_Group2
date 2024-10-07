package com.shoppingapp.shoppingapp.mapper;

import com.shoppingapp.shoppingapp.dto.request.AddressCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.AddressUpdateRequest;
import com.shoppingapp.shoppingapp.dto.request.ShopCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.ShopUpdateRequest;
import com.shoppingapp.shoppingapp.dto.response.AddressResponse;
import com.shoppingapp.shoppingapp.dto.response.ShopResponse;
import com.shoppingapp.shoppingapp.models.Address;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")

public interface AddressMapper {
    @Mapping(target = "user", ignore = true)
    Address toAddress(AddressCreationRequest AddressCreationRequest);

    void updateAddress(@MappingTarget Address address, AddressUpdateRequest AddressUpdateRequest);
    AddressResponse toResponse(Address address);

}
