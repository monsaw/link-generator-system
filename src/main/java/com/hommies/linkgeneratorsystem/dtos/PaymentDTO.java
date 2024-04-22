package com.hommies.linkgeneratorsystem.dtos;

import com.hommies.linkgeneratorsystem.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDTO {
    private String paymentReference;
    private String invoiceReference;
    private String transactionLink;
    private BigDecimal totalAmount;
    private String merchantCode;
    private String payerName;
    private Status status;
    private LocalDateTime createdAt;
}
