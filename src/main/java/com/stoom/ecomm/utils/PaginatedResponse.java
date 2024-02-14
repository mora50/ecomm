package com.stoom.ecomm.utils;

import java.util.List;

public record PaginatedResponse<T>(
        List<T> content,
        boolean last,
        int totalPages,
        long totalElements,
        boolean first,
        int size,
        int number,
        boolean empty,
        int numberOfElements
) {
}