package com.apextech.productservice.service.impl;

import com.apextech.productservice.constant.ProductStatusEnum;
import com.apextech.productservice.entity.ProductEntity;
import com.apextech.productservice.exception.ResourceNotFoundException;
import com.apextech.productservice.model.request.ProductCreateRequest;
import com.apextech.productservice.model.request.ProductDeleteRequest;
import com.apextech.productservice.model.request.ProductListRequest;
import com.apextech.productservice.model.request.ProductUpdateRequest;
import com.apextech.productservice.model.response.ListResponse;
import com.apextech.productservice.model.response.ProductResponse;
import com.apextech.productservice.repository.ProductCategoryRepository;
import com.apextech.productservice.repository.ProductRepository;
import com.apextech.productservice.service.ProductService;
import com.apextech.productservice.utils.NumberGeneratorUtil;
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
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;

    @Override
    @Transactional
    public ProductResponse create(ProductCreateRequest request) {
        try{
            String productCode = generateProductNumber();
            // validate product code
            if (productRepository.existsByProductCode(productCode)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Product code already exists:" + productCode
                );
            }

            // validate category
            if (!productCategoryRepository.existsByCategoryCode(request.getCategoryId())) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Product Category code not exists:" + request.getCategoryId()
                );
            }

            ProductEntity product = ProductEntity.builder()
                    .productCode(productCode)
                    .productName(request.getProductName())
                    .slug(generateSlug(request.getProductName()))
                    .categoryId(request.getCategoryId())
                    .brandName(request.getBrandName())
                    .description(request.getDescription())
                    .price(request.getPrice())
                    .compareAtPrice(request.getCompareAtPrice())
                    .currency(request.getCurrency().toUpperCase())
                    .productStatus(request.getProductStatus())
                    .isFeatured(Boolean.TRUE.equals(request.getIsFeatured()))
                    .createdOn(OffsetDateTime.now())
                    .build();

            ProductEntity savedProduct = productRepository.save(product);

            log.info("Product created successfully. Product Code: {}", savedProduct.getProductCode());

            return toResponse(savedProduct);
        }catch(Exception e){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Exception error:" + e.getMessage()
            );
        }
    }

    @Override
    @Transactional
    public ProductResponse update(ProductUpdateRequest request) {
        try{
            ProductEntity product = getProduct(request.getProductCode());

            product.setProductName(request.getProductName());
            product.setSlug(generateSlug(request.getProductName()));
            product.setCategoryId(request.getCategoryId());
            product.setBrandName(request.getBrandName());
            product.setDescription(request.getDescription());
            product.setPrice(request.getPrice());
            product.setCompareAtPrice(request.getCompareAtPrice());
            product.setCurrency(request.getCurrency().toUpperCase());
            product.setProductStatus(request.getProductStatus());
            product.setFeatured(request.getIsFeatured());
            product.setModifiedOn(OffsetDateTime.now());

            ProductEntity updatedProduct = productRepository.save(product);

            log.info("Product updated successfully. Product Code: {}", updatedProduct.getProductCode());

            return toResponse(updatedProduct);
        }catch(Exception e){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Exception error:" + e.getMessage()
            );
        }
    }

    @Override
    public void delete(ProductDeleteRequest request) {
        try {
            String productCode = request.getProductCode();
            ProductEntity deletedProduct = productRepository.findFirstByProductCode(productCode)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Product not found: " + productCode
                    ));
            deletedProduct.setProductStatus(ProductStatusEnum.PD.getValue());
            productRepository.save(deletedProduct);

            log.info("Product deleted successfully. Product Code: {}", productCode);

        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Exception error:" + e.getMessage()
            );
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ListResponse<ProductResponse> list(ProductListRequest request) {
        try {
            int page = request.getPage() == null ? 0 : request.getPage();
            int size = request.getSize() == null ? 10 : request.getSize();

            if (page < 0) {
                throw new ResourceNotFoundException("Page must not be negative");
            }

            if (size < 1 || size > 100) {
                throw new ResourceNotFoundException("Page size must be between 1 and 100");
            }

            Pageable pageable = PageRequest.of(page, size);
            Page<ProductEntity> list = productRepository.findAllByOrderByIdDesc(pageable);

            log.info("Total data size:{}", list.getSize());

            List<ProductResponse> content = list.getContent()
                    .stream()
                    .map(this::toResponse)
                    .toList();

            return ListResponse.<ProductResponse>builder()
                    .content(content)
                    .totalElements(list.getTotalElements())
                    .totalPages(list.getTotalPages())
                    .build();
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Exception error:" + e.getMessage()
            );
        }
    }

    private ProductEntity getProduct(String productId) {

        return productRepository.findFirstByProductCode(productId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product not found: " + productId
                ));
    }

    private String generateProductNumber() {
        return NumberGeneratorUtil.generate(
                "PRD",
                productRepository.nextProductSequence()
        );
    }


    private ProductResponse toResponse(ProductEntity product) {
        return ProductResponse.builder()
                .id(product.getId())
                .productCode(product.getProductCode())
                .productName(product.getProductName())
                .slug(product.getSlug())
                .categoryId(product.getCategoryId())
                .brandName(product.getBrandName())
                .description(product.getDescription())
                .price(product.getPrice())
                .compareAtPrice(product.getCompareAtPrice())
                .currency(product.getCurrency())
                .productStatus(product.getProductStatus())
                .isFeatured(product.isFeatured())
                .createdOn(product.getCreatedOn())
                .build();
    }

    private String generateSlug(String productName) {
        return productName.trim()
                .toLowerCase()
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("(^-|-$)", "");
    }
}
