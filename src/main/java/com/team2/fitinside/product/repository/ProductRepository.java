package com.team2.fitinside.product.repository;

import com.team2.fitinside.category.entity.Category;
import com.team2.fitinside.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

//    List<Product> findByIsDeletedFalse();

    // 삭제되지 않은 상품들만 조회 (페이지네이션 적용)
    Page<Product> findByIsDeletedFalse(Pageable pageable);

    // 삭제되지 않은 상품 중 이름 또는 설명에 키워드가 포함된 상품 검색
    @Query("SELECT p FROM Product p WHERE (p.isDeleted = false) AND (p.productName LIKE %:keyword% OR p.info LIKE %:keyword%)")
    Page<Product> searchByKeywordAndIsDeletedFalse(@Param("keyword") String keyword, Pageable pageable);

    // 삭제되지 않은 특정 카테고리의 상품 중 이름 또는 설명에 키워드가 포함된 상품 검색
    @Query("SELECT p FROM Product p WHERE (p.isDeleted = false) AND p.category = :category AND (p.productName LIKE %:keyword% OR p.info LIKE %:keyword%)")
    Page<Product> searchByKeywordAndCategoryAndIsDeletedFalse(@Param("category") Category category, @Param("keyword") String keyword, Pageable pageable);

    // 삭제되지 않은 특정 카테고리의 상품 조회 (페이지네이션 적용)
    Page<Product> findByCategoryAndIsDeletedFalse(Category category, Pageable pageable);
}
