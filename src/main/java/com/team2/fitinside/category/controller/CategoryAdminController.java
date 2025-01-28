package com.team2.fitinside.category.controller;

import com.team2.fitinside.category.dto.CategoryCreateRequestDTO;
import com.team2.fitinside.category.dto.CategoryUpdateRequestDTO;
import com.team2.fitinside.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/admin/categories")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "https://fit-inside.netlify.app"})
public class CategoryAdminController {

    private final CategoryService categoryService;

    // 카테고리 생성 엔드포인트
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CategoryCreateRequestDTO> createCategory(
            @RequestParam("name") String name,
            @RequestParam("displayOrder") Long displayOrder,
            @RequestParam(value = "mainDisplayOrder", required = false) Long mainDisplayOrder,
            @RequestParam(value = "isDeleted", required = false) Boolean isDeleted,
            @RequestParam(value = "parentId", required = false) Long parentId,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {

        CategoryCreateRequestDTO createdCategory = categoryService.createCategory(name, displayOrder, mainDisplayOrder, isDeleted, parentId, imageFile);
        return ResponseEntity.ok(createdCategory);
    }

    // 카테고리 수정
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CategoryUpdateRequestDTO> updateCategory(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("displayOrder") Long displayOrder,
            @RequestParam(value = "mainDisplayOrder", required = false) Long mainDisplayOrder,
            @RequestParam(value = "isDeleted", required = false) Boolean isDeleted,
            @RequestParam(value = "parentId", required = false) Long parentId,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {

        CategoryUpdateRequestDTO updatedCategory = categoryService.updateCategory(
                id, name, displayOrder, mainDisplayOrder, isDeleted, parentId, imageFile);

        return ResponseEntity.ok(updatedCategory);
    }

    // 카테고리 삭제 엔드포인트
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("카테고리가 삭제되었습니다.");
    }
}
