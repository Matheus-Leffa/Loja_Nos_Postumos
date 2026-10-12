package com.nospostumos.loja.dto;

import com.nospostumos.loja.entity.Size;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductMeasurementRequest(
        @NotNull Size size,
        @NotNull @DecimalMin(value = "0.0", inclusive = false) BigDecimal bust,
        @NotNull @DecimalMin(value = "0.0", inclusive = false) BigDecimal waist,
        @NotNull @DecimalMin(value = "0.0", inclusive = false) BigDecimal length
) {
}
