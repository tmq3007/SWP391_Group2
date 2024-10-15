package com.shoppingapp.shoppingapp.repository;

import com.shoppingapp.shoppingapp.models.Address;
import com.shoppingapp.shoppingapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    public List<Address> findAddressByUser(User user);
}
