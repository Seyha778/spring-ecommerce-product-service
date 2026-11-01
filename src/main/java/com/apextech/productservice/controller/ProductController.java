package com.apextech.productservice.controller;

import com.apextech.productservice.common.ResponseMessage;
import com.apextech.productservice.common.ResponseMessageBuilder;
import com.apextech.productservice.model.request.ProductCreateRequest;
import com.apextech.productservice.model.request.ProductDeleteRequest;
import com.apextech.productservice.model.request.ProductListRequest;
import com.apextech.productservice.model.request.ProductUpdateRequest;
import com.apextech.productservice.model.response.ListResponse;
import com.apextech.productservice.model.response.ProductResponse;
import com.apextech.productservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/create")
    public ResponseMessage<ProductResponse> create(@Valid @RequestBody ProductCreateRequest request) {
        ProductResponse response = productService.create(request);
        return new ResponseMessageBuilder<ProductResponse>()
                .success()
                .data(response)
                .build();
    }

    @PostMapping("/update")
    public ResponseMessage<ProductResponse> update(@Valid @RequestBody ProductUpdateRequest request) {
        ProductResponse response = productService.update(request);
        return new ResponseMessageBuilder<ProductResponse>()
                .success()
                .data(response)
                .build();
    }

    @PostMapping("/delete")
    public ResponseMessage<?> delete(@Valid @RequestBody ProductDeleteRequest request) {
        productService.delete(request);
        return new ResponseMessageBuilder<ProductResponse>()
                .success()
                .data(null)
                .build();
    }

    @PostMapping("/list")
    public ResponseMessage<ListResponse<ProductResponse>> list(@Valid @RequestBody ProductListRequest request) {
        ListResponse<ProductResponse> response = productService.list(request);
        return new ResponseMessageBuilder<ListResponse<ProductResponse>>()
                .success()
                .data(response)
                .build();
    }
}
