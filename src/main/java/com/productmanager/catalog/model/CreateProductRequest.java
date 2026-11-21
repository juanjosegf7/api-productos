package com.productmanager.catalog.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateProductRequest(
        @NotBlank @Size(max = 120) String name,
        @NotNull @DecimalMin(value = "0.0", inclusive = true) Double price,
        @NotNull @jakarta.validation.constraints.Min(0) Integer stock,
        @NotBlank @Size(max = 80) String category) {
}
