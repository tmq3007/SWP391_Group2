package com.shoppingapp.shoppingapp.controllers;

import com.shoppingapp.shoppingapp.dto.request.ApiResponse;
import com.shoppingapp.shoppingapp.dto.request.AddressCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.AddressUpdateRequest;
import com.shoppingapp.shoppingapp.dto.response.AddressResponse;
import com.shoppingapp.shoppingapp.dto.response.ShopResponse;
import com.shoppingapp.shoppingapp.dto.response.WishlistResponse;
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
    public ApiResponse<List<AddressResponse>> getAllAddresses() {
        ApiResponse<List<AddressResponse>> apiResponse = new ApiResponse<>();
        return ApiResponse.<List<AddressResponse>>builder()
                .result(addressService.getAllAddress())
                .build();

    }

    @GetMapping("/user/{userId}")
    ApiResponse<List<AddressResponse>> getAddressByUserId(@PathVariable("userId") Long userId) {
        return ApiResponse.<List<AddressResponse>>builder().result(addressService.getAddressByUserId(userId)).build();
    }

    // Get address by ID
    @GetMapping("{addressId}")
    ApiResponse<?> getAddress(@PathVariable("addressId") Long addressId) {
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
    @PatchMapping
    public ResponseEntity<?> updateAddress(@RequestBody Address request) {
        // Log request details for debugging
        System.out.println("Full Address Details: "
                + request.getAddressID() + "\n"
                + request.getCity() + "\n"
                + request.getDistrict() + "\n"
                + request.getStreet() + "\n"
                + request.getSubDistrict());
        addressService.updateAddress(request);
        // Call the service to update the address and return the response
        return ResponseEntity.ok("Great!");
    }

    // Delete an address by ID
    @DeleteMapping("/{addressId}")
    ApiResponse<String> deleteAddress(@PathVariable("addressId") Long addressId) {
        System.out.println(addressId);
        addressService.deleteAddress(addressId);
        return ApiResponse.<String>builder().result("Address is deleted").build();
    }
}
