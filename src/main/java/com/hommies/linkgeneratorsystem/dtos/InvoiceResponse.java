package com.hommies.linkgeneratorsystem.dtos;


import com.hommies.linkgeneratorsystem.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceResponse {
    private String transactionLink;
    private String invoiceReference;
    private Status invoiceStatus;
    private BigDecimal totalAmount;

}
