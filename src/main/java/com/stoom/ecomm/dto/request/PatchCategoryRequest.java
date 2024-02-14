package com.stoom.ecomm.dto.request;

import jakarta.validation.constraints.NotNull;

public record PatchCategoryRequest(
        @NotNull
        Long id,

        String name,

        String imgUrl,

        Boolean active) {
}