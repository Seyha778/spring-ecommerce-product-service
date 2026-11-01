package com.apextech.productservice.service.impl;

import com.apextech.productservice.constant.CategoryStatusEnum;
import com.apextech.productservice.entity.ProductCategoryEntity;
import com.apextech.productservice.exception.ResourceNotFoundException;
import com.apextech.productservice.model.request.ProductCategoryCreateRequest;
import com.apextech.productservice.model.request.ProductCategoryDeleteRequest;
import com.apextech.productservice.model.request.ProductCategoryUpdateRequest;
import com.apextech.productservice.model.request.ProductListRequest;
import com.apextech.productservice.model.response.ListResponse;
import com.apextech.productservice.model.response.ProductCategoryResponse;
import com.apextech.productservice.repository.ProductCategoryRepository;
import com.apextech.productservice.repository.ProductRepository;
import com.apextech.productservice.service.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;

    @Override
    @Transactional
    public ProductCategoryResponse create(ProductCategoryCreateRequest request) {

        // validate product code
        if (productCategoryRepository.existsByCategoryCode(request.getCategoryCode())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Category code already exists:" + request.getCategoryCode()
            );
        }

        ProductCategoryEntity productCategory = ProductCategoryEntity.builder()
                .categoryCode(request.getCategoryCode())
                .categoryName(request.getCategoryName())
                .description(request.getDescription())
                .parentCategoryId(request.getParentCategoryId())
                .imageUrl(request.getImageUrl())
                .displayOrder(request.getDisplayOrder())
                .createdOn(OffsetDateTime.now())
                .build();

        ProductCategoryEntity savedProduct = productCategoryRepository.save(productCategory);

        log.info("Product Category created successfully. Product Code: {}", savedProduct.getCategoryCode());

        return toResponse(savedProduct);
    }

    @Override
    @Transactional
    public ProductCategoryResponse update(ProductCategoryUpdateRequest request) {

        ProductCategoryEntity category = getProductCategory(request.getCategoryCode());

        category.setCategoryCode(request.getCategoryCode());
        category.setCategoryName(request.getCategoryName());
        category.setParentCategoryId(request.getParentCategoryId());
        category.setDescription(request.getDescription());
        category.setDisplayOrder(request.getDisplayOrder());
        category.setImageUrl(request.getImageUrl());
        category.setStatus(request.getStatus());
        category.setModifiedOn(OffsetDateTime.now());

        ProductCategoryEntity updatedCategory = productCategoryRepository.save(category);

        log.info("Product Category updated successfully. Category Code: {}", updatedCategory.getCategoryCode());

        return toResponse(updatedCategory);
    }

    @Override
    public void delete(ProductCategoryDeleteRequest request) {
        String categoryCode = request.getCategoryCode();
        ProductCategoryEntity deletedProduct = productCategoryRepository.findFirstByCategoryCode(categoryCode)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product Category not found: " + categoryCode
                ));
        deletedProduct.setStatus(CategoryStatusEnum.CD.getValue());
        productCategoryRepository.save(deletedProduct);

        log.info("Product Category deleted successfully. Category Code: {}", categoryCode);
    }

    private ProductCategoryEntity getProductCategory(String categoryCode) {

        return productCategoryRepository.findFirstByCategoryCode(categoryCode)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category Code not found: " + categoryCode
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public ListResponse<ProductCategoryResponse> list(ProductListRequest request) {

        int page = request.getPage() == null ? 0 : request.getPage();
        int size = request.getSize() == null ? 10 : request.getSize();

        if (page < 0) {
            throw new ResourceNotFoundException("Page must not be negative");
        }

        if (size < 1 || size > 100) {
            throw new ResourceNotFoundException("Page size must be between 1 and 100");
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<ProductCategoryEntity> list = productCategoryRepository.findAllByOrderByIdDesc(pageable);

        log.info("Total data size:{}", list.getSize());

        List<ProductCategoryResponse> content = list.getContent()
                .stream()
                .map(this::toResponse)
                .toList();

        return ListResponse.<ProductCategoryResponse>builder()
                .content(content)
                .totalElements(list.getTotalElements())
                .totalPages(list.getTotalPages())
                .build();
    }

    private ProductCategoryResponse toResponse(ProductCategoryEntity category) {
        return ProductCategoryResponse.builder()
                .id(category.getId())
                .categoryCode(category.getCategoryCode())
                .categoryName(category.getCategoryName())
                .description(category.getDescription())
                .parentCategoryId(category.getParentCategoryId())
                .imageUrl(category.getImageUrl())
                .description(category.getDescription())
                .status(category.getStatus())
                .displayOrder(category.getDisplayOrder())
                .createdOn(category.getCreatedOn())
                .modifiedOn(category.getModifiedOn())
                .build();
    }

}
