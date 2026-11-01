package com.apextech.productservice.repository;

import com.apextech.productservice.entity.ProductCategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategoryEntity, Long> {

    boolean existsByCategoryCode(String categoryCode);

    Optional<ProductCategoryEntity> findFirstByCategoryCode(String categoryCode);

    Page<ProductCategoryEntity> findAllByOrderByIdDesc(Pageable pageable);

    @Query(value = """
        SELECT nextval('ecommerce.category_number_seq')
        """, nativeQuery = true)
    Long nextCategorySequence();

}
