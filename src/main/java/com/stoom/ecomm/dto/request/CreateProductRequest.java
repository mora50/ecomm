package com.stoom.ecomm.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Set;

public record CreateProductRequest(
        @NotBlank(message = "name is required")
        String name,

        @PositiveOrZero
        @NotNull(message = "price is required")
        Double price,

        @NotNull(message = "active is required")
        Boolean active,

        @NotBlank(message = "name is required")
        String description,

        @NotBlank(message = "name is required")
        String sku,

        @NotBlank(message = "name is required")
        String imgUrl,

        @NotNull(message = "brand id is required")
        Long brandId,

        @NotEmpty(message = "categories ids is required")
        Set<Long> categoriesId
) {
}