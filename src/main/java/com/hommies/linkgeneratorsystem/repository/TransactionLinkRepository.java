package com.hommies.linkgeneratorsystem.repository;

import com.hommies.linkgeneratorsystem.model.TransactionLink;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionLinkRepository extends JpaRepository<TransactionLink, Long> {
    Optional<TransactionLink> findByLink(String link);
}
