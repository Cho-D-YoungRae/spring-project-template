package com.example.common.domain;


public record EnumMapperValue(
        String code,
        String title
) {

    public static EnumMapperValue of(final EnumMapperType enumMapperType) {
        return new EnumMapperValue(enumMapperType.getCode(), enumMapperType.getTitle());
    }
}
