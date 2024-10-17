package com.shoppingapp.shoppingapp.controllers;

import com.shoppingapp.shoppingapp.dto.request.ApiResponse;
import com.shoppingapp.shoppingapp.dto.request.AddressCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.AddressUpdateRequest;
import com.shoppingapp.shoppingapp.dto.response.AddressResponse;
import com.shoppingapp.shoppingapp.models.Address;
import com.shoppingapp.shoppingapp.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    // Get all addresses
    @GetMapping
    public ResponseEntity<List<Address>> getAllAddresses() {
        return ResponseEntity.ok(addressService.getAllAddress());
    }

    // Get address by ID
    @GetMapping("{addressId}")
    ApiResponse<AddressResponse> getAddress(@PathVariable("addressId") Long addressId) {
        return ApiResponse.<AddressResponse>builder().result(addressService.getAddressById(addressId)).build();
    }

    // Add new address
    @PostMapping("")
    ApiResponse<AddressResponse> createAddress(@RequestBody AddressCreationRequest request) {
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setResult(addressService.createAddress(request));
        return apiResponse;
    }

    // Update an address by ID
    @PatchMapping("/{addressId}")
    ResponseEntity<AddressResponse> updateAddress(
            @RequestBody AddressUpdateRequest request,
            @PathVariable("addressId") Long addressId
    ) {
        return ResponseEntity.ok(addressService.updateAddress(request, addressId));
    }

    // Delete an address by ID
    @DeleteMapping("/{addressId}")
    ApiResponse<String> deleteAddress(@PathVariable("addressId") Long addressId) {
        addressService.deleteAddress(addressId);
        return ApiResponse.<String>builder().result("Address is deleted").build();
    }
}
