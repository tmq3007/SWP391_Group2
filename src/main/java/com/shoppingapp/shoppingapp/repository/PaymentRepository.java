package com.shoppingapp.shoppingapp.repository;

import com.shoppingapp.shoppingapp.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
