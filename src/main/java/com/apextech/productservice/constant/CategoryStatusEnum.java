package com.apextech.productservice.constant;

import com.apextech.productservice.exception.ResourceNotFoundException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum CategoryStatusEnum {
    CA("CA", "Category Active"),
    CD("CD", "Category Delete"),
    CI("CI", "Category Progress");

    final String value;
    final String description;

    CategoryStatusEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public static String getDescriptionByValue(String value) {
        return Arrays.stream(values())
                .filter(type -> type.value.equalsIgnoreCase(value))
                .map(CategoryStatusEnum::getDescription)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Invalid Category Status: " + value
                ));
    }

    @Override
    public String toString() {
        return value;
    }
}
