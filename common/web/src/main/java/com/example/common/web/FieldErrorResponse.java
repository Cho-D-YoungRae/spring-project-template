package com.example.common.web;

public record FieldErrorResponse(
        String field,
        Object value,
        String reason
) {
}
