package com.stoom.ecomm.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Set;

public record CreateProductRequest(
        @NotBlank(message = "name is required")
        String name,

        @PositiveOrZero
        @NotBlank(message = "price is required")
        Double price,

        @NotBlank(message = "name is required")
        String description,

        @NotBlank(message = "name is required")
        String sku,

        @NotBlank(message = "name is required")
        String imgUrl,

        @NotBlank(message = "brand id is required")
        Long brandId,

        @NotBlank(message = "categories ids is required")
        Set<Long> categoriesId
) {
}