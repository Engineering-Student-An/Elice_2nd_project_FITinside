package com.team2.fitinside.product.controller;

import com.team2.fitinside.product.dto.ProductResponseDto;
import com.team2.fitinside.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    // 페이지네이션, 정렬, 검색을 적용한 상품 목록 조회
    @GetMapping
    @Operation(summary = "상품 목록 조회", description = "등록된 모든 상품 목록을 페이지네이션, 정렬, 검색 기능과 함께 반환합니다.")
    @ApiResponse(responseCode = "200", description = "상품 목록 조회 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponseDto.class)))
    public ResponseEntity<Page<ProductResponseDto>> getAllProducts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "6") int size,
            @RequestParam(value = "sortField", defaultValue = "createdAt") String sortField,
            @RequestParam(value = "sortDir", defaultValue = "desc") String sortDir,
            @RequestParam(value = "keyword", required = false) String keyword) {

        Page<ProductResponseDto> products = productService.getAllProducts(page, size, sortField, sortDir, keyword);
        return ResponseEntity.ok(products);
    }

    // 페이지네이션, 정렬, 검색을 적용한 상품 목록 조회
    @GetMapping("/byCategory")
    @Operation(summary = "상품 목록 조회", description = "등록된 모든 상품 목록을 페이지네이션, 정렬, 검색 기능과 함께 반환합니다.")
    @ApiResponse(responseCode = "200", description = "상품 목록 조회 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponseDto.class)))
    public ResponseEntity<Page<ProductResponseDto>> getAllProductsByCategoryName(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "9") int size,
            @RequestParam(value = "sortField", defaultValue = "createdAt") String sortField,
            @RequestParam(value = "sortDir", defaultValue = "desc") String sortDir,
            @RequestParam(value = "keyword", required = false) String keyword) {

        Page<ProductResponseDto> products = productService.getAllProductsByCategoryName(page, size, sortField, sortDir, keyword);
        return ResponseEntity.ok(products);
    }



    // 페이지네이션, 정렬, 검색을 적용한 특정 카테고리 상품 목록 조회
    @GetMapping("/category/{categoryId}")
    @Operation(summary = "카테고리별 상품 목록 조회", description = "특정 카테고리의 상품 목록을 페이지네이션, 정렬, 검색 기능과 함께 반환합니다.")
    @ApiResponse(responseCode = "200", description = "상품 목록 조회 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponseDto.class)))
    public ResponseEntity<Page<ProductResponseDto>> getProductsByCategory(
            @PathVariable("categoryId") Long categoryId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "9") int size,
            @RequestParam(value = "sortField", defaultValue = "createdAt") String sortField,
            @RequestParam(value = "sortDir", defaultValue = "desc") String sortDir,
            @RequestParam(value = "keyword", required = false) String keyword) {

        Page<ProductResponseDto> products = productService.getProductsByCategory(categoryId, page, size, sortField, sortDir, keyword);
        return ResponseEntity.ok(products);
    }

    // 상품 상세 조회
    @GetMapping("/{id}")
    @Operation(summary = "상품 상세 조회", description = "상품의 상세 정보를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "상품 상세 조회 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponseDto.class)))
    @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없음", content = @Content(mediaType = "application/json"))
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable("id") Long id) {
        ProductResponseDto product = productService.findProductById(id);
        return ResponseEntity.ok(product);
    }
}
