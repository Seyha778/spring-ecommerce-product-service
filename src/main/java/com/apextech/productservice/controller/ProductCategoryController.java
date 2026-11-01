package com.apextech.productservice.controller;

import com.apextech.productservice.common.ResponseMessage;
import com.apextech.productservice.common.ResponseMessageBuilder;
import com.apextech.productservice.model.request.ProductCategoryCreateRequest;
import com.apextech.productservice.model.request.ProductCategoryDeleteRequest;
import com.apextech.productservice.model.request.ProductCategoryUpdateRequest;
import com.apextech.productservice.model.request.ProductListRequest;
import com.apextech.productservice.model.response.ListResponse;
import com.apextech.productservice.model.response.ProductCategoryResponse;
import com.apextech.productservice.service.ProductCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    @PostMapping("/create")
    public ResponseMessage<ProductCategoryResponse> create(@Valid @RequestBody ProductCategoryCreateRequest request) {
        ProductCategoryResponse response = productCategoryService.create(request);
        return new ResponseMessageBuilder<ProductCategoryResponse>()
                .success()
                .data(response)
                .build();
    }

    @PostMapping("/update")
    public ResponseMessage<ProductCategoryResponse> update(@Valid @RequestBody ProductCategoryUpdateRequest request) {
        ProductCategoryResponse response = productCategoryService.update(request);
        return new ResponseMessageBuilder<ProductCategoryResponse>()
                .success()
                .data(response)
                .build();
    }

    @PostMapping("/delete")
    public ResponseMessage<?> delete(@Valid @RequestBody ProductCategoryDeleteRequest request) {
        productCategoryService.delete(request);
        return new ResponseMessageBuilder<ProductCategoryResponse>()
                .success()
                .data(null)
                .build();
    }

    @PostMapping("/list")
    public ResponseMessage<ListResponse<ProductCategoryResponse>> list(@Valid @RequestBody ProductListRequest request) {
        ListResponse<ProductCategoryResponse> response = productCategoryService.list(request);
        return new ResponseMessageBuilder<ListResponse<ProductCategoryResponse>>()
                .success()
                .data(response)
                .build();
    }
}
