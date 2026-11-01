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
public class ProductCategoryResponse {

    private Long id;

    private String categoryCode;

    private String categoryName;

    private String description;

    private String parentCategoryId;

    private String imageUrl;

    private Integer displayOrder;

    private String status;

    private OffsetDateTime createdOn;

    private OffsetDateTime modifiedOn;
}
