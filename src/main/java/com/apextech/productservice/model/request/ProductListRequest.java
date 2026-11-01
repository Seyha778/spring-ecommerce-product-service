package com.apextech.productservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductListRequest {
    private String keyword;

    private String categoryId;

    private String brandName;

    private String productStatus;

    @Builder.Default
    private Integer page = 0;

    @Builder.Default
    private Integer size = 10;

}
