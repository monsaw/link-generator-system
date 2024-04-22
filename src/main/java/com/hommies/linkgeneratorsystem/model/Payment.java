package com.hommies.linkgeneratorsystem.model;


import com.hommies.linkgeneratorsystem.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment extends BaseEntity{
    private String invoiceReference;
    private String merchantCode;
    private String transactionLink;
    private BigDecimal totalAmount;
    private String payerName;
    private Status status;
    private LocalDateTime createdAt;


}
