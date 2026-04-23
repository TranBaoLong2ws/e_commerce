package com.TranBaoLong2ws.springboot.E_commerce.Request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class ProductRequest {

    @NotEmpty(message = "Name is mandatory")
    @Size(min = 3, max = 40, message = "Name must be at least 3 characters long")
    private String name;

    @NotEmpty(message = "Description is mandatory")
    @Size(min = 3, max = 40, message = "Description must be at least 3 characters long")
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;

    @Min(value = 0, message = "Stock must be >= 0")
    private int stock;

    @NotNull(message = "Add category type")
    @Min(value = 0, message = "Category must be >= 0")
    private long idCategory;


    public ProductRequest() {}
}
