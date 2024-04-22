package com.hommies.linkgeneratorsystem.repository;


import com.hommies.linkgeneratorsystem.model.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByTransactionLink(String link);

    Page<Payment> findAllByMerchantCode(String merchantCode, Pageable pageable);



}
