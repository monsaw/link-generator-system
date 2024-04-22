package com.hommies.linkgeneratorsystem.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentRequest {
    @NotBlank(message = "Transaction Link cannot be Blank")
    @NotEmpty(message = "Transaction Link cannot be Empty")
    private String transactionLink;

    @NotBlank(message = "Amount cannot be Blank")
    @NotEmpty(message = "Amount cannot be Empty")
    private String amount;
}
