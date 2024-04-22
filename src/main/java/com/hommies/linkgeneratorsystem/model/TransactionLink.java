package com.hommies.linkgeneratorsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "transaction_link")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionLink extends BaseEntity{
    private String link;
    private String invoiceReference;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;
    private boolean used;

    @ManyToOne
    @JsonIgnore
    private Invoice invoice;
}
