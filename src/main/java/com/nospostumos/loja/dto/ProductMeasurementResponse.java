package com.nospostumos.loja.dto;

import com.nospostumos.loja.entity.Size;

import java.math.BigDecimal;

public record ProductMeasurementResponse(
        Size size,
        BigDecimal bust,
        BigDecimal waist,
        BigDecimal length
) {
}
