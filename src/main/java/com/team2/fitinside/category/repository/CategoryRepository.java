package com.team2.fitinside.category.repository;
import com.team2.fitinside.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Soft delete가 되지 않은 카테고리만 조회
    List<Category> findAllByIsDeletedFalse();

    // Soft delete가 되지 않은 특정 ID의 카테고리 조회
    Optional<Category> findByIdAndIsDeletedFalse(Long id);
}