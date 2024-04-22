package com.hommies.linkgeneratorsystem.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class OnboardMerchantRequest {
    @NotBlank(message = "First Name cannot be Blank")
    @NotEmpty(message = "First Name cannot be Empty")
    private String firstName;

    @NotBlank(message = "Last Name cannot be Blank")
    @NotEmpty(message = "Last Name cannot be Empty")
    private String lastName;

    @NotBlank(message = "Company Name cannot be Blank")
    @NotEmpty(message = "Company Name cannot be Empty")
    private String companyName;

    @NotBlank(message = "Company Unique Code cannot be Blank")
    @NotEmpty(message = "Company Unique Code cannot be Empty")
    @Size(min = 4, max = 4, message = "Company Unique Code must be 4 character long")
    private String merchantCode;
}
