package com.hommies.linkgeneratorsystem.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "merchant")
@Builder
public class Merchant extends BaseEntity {

    private String firstName;
    private String lastName;
    private String companyName;
    private String merchantCode;
    private LocalDateTime createdAt;

}
