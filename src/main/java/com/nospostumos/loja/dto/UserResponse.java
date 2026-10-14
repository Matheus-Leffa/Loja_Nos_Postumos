package com.nospostumos.loja.dto;

import com.nospostumos.loja.entity.UserRole;

public record UserResponse(
        Long id,
        String email,
        UserRole role,
        boolean active
) {
}
