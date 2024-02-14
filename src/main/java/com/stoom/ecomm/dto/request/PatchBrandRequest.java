package com.stoom.ecomm.dto.request;

public record PatchBrandRequest(

        String name,

        String imgUrl,

        Boolean active
) {
}