package com.hommies.linkgeneratorsystem.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InvoiceItemDTO {

    @NotBlank(message = "Item name cannot be Blank")
    @NotEmpty(message = "Item name cannot be Empty")
    private String itemName;

    @NotBlank(message = "Unit Price cannot be Blank")
    @NotEmpty(message = "Unit Price cannot be Empty")
    private String unitPrice;

    @NotBlank(message = "Quantity cannot be Blank")
    @NotEmpty(message = "Quantity cannot be Empty")
    private Integer quantity;

}
