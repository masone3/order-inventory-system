package com.eastonmason.orderinventory.productservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record ProductRequest (
    @NotBlank String name,
    @NotBlank String sku,
    @NotNull @PositiveOrZero Integer stockQuantity,
    @NotNull @Positive Double price
) {}