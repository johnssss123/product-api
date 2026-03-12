package com.ecommerce.product_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    //primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="Product cannot be empt")
    @Size(min=2,max=100,message="Product must be between 2 and 100 characters")
    @Column(nullable = false)
    private String name;

    @NotBlank(message="Description cannot be empty")
    @Size(max=500,message = "Description cannot exceed 500 characters")
    @Column(nullable = false)
    private String description;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value= "0.1" ,message ="Price must be greater than 0" )
    @Column(nullable = false)
    private Double price;

    @NotNull(message = "Quantity cannot be null")
    @Min(value = 0,message = "quantity cannot be negative")
    @Column(nullable = false)
    private Integer quantity;
}
