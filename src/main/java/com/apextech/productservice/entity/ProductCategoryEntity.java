package com.apextech.productservice.entity;

import com.apextech.productservice.constant.CategoryStatusEnum;
import com.apextech.productservice.constant.ProductStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;

@Entity
@Table(
        name = "product_categories",
        schema = "ecommerce"
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @Column(name = "category_code", nullable = false)
    private String categoryCode;

    @Column(name = "description")
    private String description;

    @Column(name = "parent_category_id")
    private String parentCategoryId;

    @Column(name = "image_url")
    private String imageUrl;

    @Builder.Default
    @Column(name = "status", nullable = false)
    private String status = CategoryStatusEnum.CA.getValue();;

    @Column(name = "display_order")
    private Integer displayOrder;

    @Column(name = "created_by")
    private String createdBy;

    @CreationTimestamp
    @Column(name = "created_on", nullable = false, updatable = false)
    private OffsetDateTime createdOn;

    @Column(name = "modified_by")
    private String modifiedBy;

    @UpdateTimestamp
    @Column(name = "modified_on")
    private OffsetDateTime modifiedOn;

}