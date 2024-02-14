package com.stoom.ecomm.exceptions;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArgumentsException {

    public static Map<String, Object> buildErrorResponse(BindingResult bindingResult) {
        List<Map<String, Object>> errors = new ArrayList<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("field", fieldError.getField());
            errorMap.put("message", fieldError.getDefaultMessage());
            errors.add(errorMap);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Validation error");
        response.put("errors", errors);

        return response;
    }
}