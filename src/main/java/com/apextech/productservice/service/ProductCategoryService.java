package com.apextech.productservice.service;

import com.apextech.productservice.model.request.ProductCategoryCreateRequest;
import com.apextech.productservice.model.request.ProductCategoryDeleteRequest;
import com.apextech.productservice.model.request.ProductCategoryUpdateRequest;
import com.apextech.productservice.model.request.ProductListRequest;
import com.apextech.productservice.model.response.ListResponse;
import com.apextech.productservice.model.response.ProductCategoryResponse;

public interface ProductCategoryService {

    ProductCategoryResponse create(ProductCategoryCreateRequest request);

    ProductCategoryResponse update(ProductCategoryUpdateRequest request);

    void delete(ProductCategoryDeleteRequest request);

    ListResponse<ProductCategoryResponse> list(ProductListRequest request);

}
