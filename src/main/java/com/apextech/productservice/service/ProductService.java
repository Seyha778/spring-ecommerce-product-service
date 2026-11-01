package com.apextech.productservice.service;

import com.apextech.productservice.model.request.ProductCreateRequest;
import com.apextech.productservice.model.request.ProductDeleteRequest;
import com.apextech.productservice.model.request.ProductListRequest;
import com.apextech.productservice.model.request.ProductUpdateRequest;
import com.apextech.productservice.model.response.ListResponse;
import com.apextech.productservice.model.response.ProductResponse;

public interface ProductService {

    ProductResponse create(ProductCreateRequest request);

    ProductResponse update(ProductUpdateRequest request);

    void delete(ProductDeleteRequest request);

    ListResponse<ProductResponse> list(ProductListRequest request);

}
