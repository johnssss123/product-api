package com.ecommerce.product_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthRequestDTO {
    private String token;
    private String username;
    private String role;
}
