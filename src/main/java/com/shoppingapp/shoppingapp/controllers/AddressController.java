package com.shoppingapp.shoppingapp.controllers;

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

    @GetMapping
    public List<Address> getAllAddresses() {
        return addressService.getAllAddresses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable Long id) {
        return ResponseEntity.ok(addressService.getAddressById(id));

    }

    @PostMapping
    public Address createAddress(@RequestBody Address address) {
        return addressService.addAddress(address);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable Long id, @RequestBody Address addressDetails) {
        return ResponseEntity.ok(addressService.updateAddress(addressDetails,id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }
}
