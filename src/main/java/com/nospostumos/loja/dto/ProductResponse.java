package com.nospostumos.loja.dto;

import java.math.BigDecimal;
import java.util.List;

public record ProductResponse(
        Long id,
        String name,
        String description,
        BigDecimal price,
        String imageUrl,
        List<ProductMeasurementResponse> measurements,
        boolean active
) {
}
