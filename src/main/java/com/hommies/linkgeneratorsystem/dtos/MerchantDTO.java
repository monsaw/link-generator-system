package com.hommies.linkgeneratorsystem.dtos;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MerchantDTO {

    private String firstName;


    private String lastName;

    private String companyName;

    private String merchantCode;

    private LocalDateTime createdAt;
}
