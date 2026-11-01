package com.apextech.productservice.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateRequest {

    @Size(max = 50)
    @JsonProperty("product_code")
    private String productCode;

    @NotBlank(message = "Product name is required")
    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("category_id")
    private String categoryId;

    @JsonProperty("brand_name")
    private String brandName;

    @JsonProperty("description")
    private String description;

    @NotNull(message = "Product price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than zero")
    @JsonProperty("price")
    private BigDecimal price;

    @DecimalMin(value = "0.00", message = "Compare price cannot be negative")
    @JsonProperty("compare_at_price")
    private BigDecimal compareAtPrice;

    @NotBlank(message = "Currency is required")
    @JsonProperty("currency")
    private String currency;

    @NotBlank(message = "Product status is required")
    @JsonProperty("product_status")
    private String productStatus;

    @JsonProperty("is_featured")
    private Boolean isFeatured;
}
