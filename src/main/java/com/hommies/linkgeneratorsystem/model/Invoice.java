package com.hommies.linkgeneratorsystem.model;

import com.hommies.linkgeneratorsystem.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "invoice")
public class Invoice extends BaseEntity {

    private String merchantCode;
    private String paymentReference;
    private String customerName;
    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<InvoiceItems> items;
    private String invoiceReference;
    private Status status;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "invoice",fetch = FetchType.EAGER)
    private List<TransactionLink> transactionLink;
    private BigDecimal totalPrice;
    private LocalDateTime createdAt;

}
