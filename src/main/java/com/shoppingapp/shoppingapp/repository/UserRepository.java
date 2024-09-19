package com.shoppingapp.shoppingapp.repository;

import com.shoppingapp.shoppingapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
