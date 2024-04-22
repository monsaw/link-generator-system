package com.hommies.linkgeneratorsystem.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "invoice_items")
public class InvoiceItems extends BaseEntity {

    private String itemName;
    private String unitPrice;
    private Integer quantity;

    @ManyToOne
    @JsonIgnore
    private Invoice invoice;




}
