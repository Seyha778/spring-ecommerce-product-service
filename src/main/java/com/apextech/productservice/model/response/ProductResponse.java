package com.apextech.productservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    private Long id;

    private String productCode;

    private String productName;

    private String slug;

    private String categoryId;

    private String brandName;

    private String description;

    private BigDecimal price;

    private BigDecimal compareAtPrice;

    private String currency;

    private String productStatus;

    private Boolean isFeatured;

    private OffsetDateTime createdOn;

    private OffsetDateTime modifiedOn;
}
