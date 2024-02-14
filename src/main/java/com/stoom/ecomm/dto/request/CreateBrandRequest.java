package com.stoom.ecomm.dto.request;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreateBrandRequest(
        @NotNull(message = "Name is required")
        String name,

        @NotEmpty(message = "Img url is required")
        String imgUrl,

        Boolean active
) {
}