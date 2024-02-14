package com.stoom.ecomm.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateCategoryRequest(
        @NotBlank(message = "name is required")
        String name,

        @NotBlank(message = "imgUrl is required")
        String imgUrl,

        Boolean active
) {
}