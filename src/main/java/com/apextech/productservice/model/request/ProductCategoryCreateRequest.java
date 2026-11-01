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
public class ProductCategoryCreateRequest {

    @NotBlank(message = "Category code is required")
    @Size(max = 50)
    @JsonProperty("category_code")
    private String categoryCode;

    @NotBlank(message = "Category name is required")
    @JsonProperty("category_name")
    private String categoryName;

    @JsonProperty("parent_category_id")
    private String parentCategoryId;

    @JsonProperty("description")
    private String description;

    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("status")
    private String status;

    @JsonProperty("display_order")
    private Integer displayOrder;

}
