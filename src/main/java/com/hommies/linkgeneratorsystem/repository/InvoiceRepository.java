package com.hommies.linkgeneratorsystem.repository;

import com.hommies.linkgeneratorsystem.model.Invoice;
import com.hommies.linkgeneratorsystem.model.TransactionLink;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice , Long> {

    Page<Invoice> findAllByMerchantCode(String merchantCode, Pageable pageable);

    Optional<Invoice> findByInvoiceReference(String invoiceReference);


}
