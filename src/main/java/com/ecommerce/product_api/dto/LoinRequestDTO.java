package com.ecommerce.product_api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoinRequestDTO {
    @NotBlank(message = "Username cannot be empty")
    private String username;

    @NotBlank(message = "Password cannot be empty")
    private String password;
}
