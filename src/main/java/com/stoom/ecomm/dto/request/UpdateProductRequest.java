package com.stoom.ecomm.dto.request;

import jakarta.validation.constraints.PositiveOrZero;

import java.util.Set;

public record UpdateProductRequest(
        String name,
        String description,
        @PositiveOrZero(message = "price must be positive or zero") Double price,
        String sku,
        String imgUrl,
        Boolean active,
        Long brandId,
        Set<Long> categoriesId
) {


}