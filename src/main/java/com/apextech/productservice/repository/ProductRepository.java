package com.apextech.productservice.repository;

import com.apextech.productservice.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    boolean existsByProductCode(String productCode);
    Optional<ProductEntity> findFirstByProductCode(String productCode);

    boolean existsBySlug(String slug);

    Page<ProductEntity> findAllByOrderByIdDesc(Pageable pageable);
}
