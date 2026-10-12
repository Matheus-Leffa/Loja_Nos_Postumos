package com.nospostumos.loja.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record ProductUpdateRequest(
        @NotBlank String name,
        @NotBlank String description,
        @NotNull @DecimalMin(value = "0.0", inclusive = false) BigDecimal price,
        @NotBlank String imageUrl,
        @NotNull Boolean active,
        @Valid List<ProductMeasurementRequest> measurements
) {
}
