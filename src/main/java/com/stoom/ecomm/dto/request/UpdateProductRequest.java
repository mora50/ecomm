package com.stoom.ecomm.dto.request;

public record UpdateProductRequest(
        String name,
        String description,
        String sku,
        String imgUrl
) {
}