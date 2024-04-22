package com.hommies.linkgeneratorsystem.dtos;

import com.hommies.linkgeneratorsystem.model.InvoiceItems;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@ToString
public class InvoiceRequest {

    @NotBlank(message = "Merchant Code cannot be Blank")
    @NotEmpty(message = "Merchant Code cannot be Empty")
    private String merchantCode;


    @NotBlank(message = "Customer Name cannot be Blank")
    @NotEmpty(message = "Customer Name cannot be Empty")
    private String customerName;


    @NotEmpty(message = "Invoice Items cannot be Empty")
    private List<InvoiceItems> items;

}
