package com.hommies.linkgeneratorsystem.dtos;

import com.hommies.linkgeneratorsystem.enums.Status;
import com.hommies.linkgeneratorsystem.model.InvoiceItems;
import com.hommies.linkgeneratorsystem.model.TransactionLink;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDTO {

    private String merchantCode;
    private String paymentReference;
    private String customerName;
    private List<InvoiceItems> items;
    private String invoiceReference;
    private List<TransactionLink> transactionLink;
    private Status status;
    private BigDecimal totalPrice;
    private LocalDateTime createdAt;
}
