package com.team2.fitinside.product.controller;

import com.team2.fitinside.product.dto.ProductInsertDto;
import com.team2.fitinside.product.dto.ProductResponseDto;
import com.team2.fitinside.product.mapper.ProductMapper;
import com.team2.fitinside.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
public class ProductAdminController {

    private final ProductService productService;

    // 생성자 주입
    @Autowired
    public ProductAdminController(ProductService productService) {
        this.productService = productService;
    }



    // 상품 등록 (관리자 전용)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "상품 등록", description = "새로운 상품을 등록하며 이미지를 함께 업로드합니다.")
    @ApiResponse(responseCode = "201", description = "상품 등록 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponseDto.class)))
    @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "404", description = "카테고리가 존재하지 않음", content = @Content(mediaType = "application/json"))
    public ResponseEntity<ProductResponseDto> createProduct(
            @ModelAttribute("productData") ProductInsertDto productInsertDto) {

        // 상품 등록 처리 (이미지 포함, 상품 설명 이미지 추가))
        ProductResponseDto createdProduct = productService.createProduct(
                ProductMapper.INSTANCE.toProductCreateDto(productInsertDto),
                productInsertDto.getProductImgUrls(),
                productInsertDto.getProductDescImgUrls()
        );
        return ResponseEntity.status(201).body(createdProduct);
    }


    // 상품 수정 (관리자 전용)
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "상품 수정", description = "기존 상품의 정보를 수정합니다. 이미지를 함께 수정할 수 있습니다.")
    @ApiResponse(responseCode = "200", description = "상품 수정 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponseDto.class)))
    @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없음", content = @Content(mediaType = "application/json"))
    public ResponseEntity<ProductResponseDto> updateProduct(
            @PathVariable Long id,
            @ModelAttribute ProductInsertDto productInsertDto) {

        // 상품 수정 처리 (이미지 포함, 상품 설명 이미지 추가)
        ProductResponseDto updatedProduct = productService.updateProduct(
                id,
                ProductMapper.INSTANCE.toProductUpdateDto(productInsertDto),
                productInsertDto.getProductImgUrls(),  // 상품 이미지
                productInsertDto.getProductDescImgUrls()  // 상품 설명 이미지
        );

        return ResponseEntity.ok(updatedProduct);
    }


    // 상품 이미지 삭제 (특정 이미지 삭제)
    @DeleteMapping("/{id}/images")
    @CrossOrigin(origins = {"http://localhost:3000", "https://fit-inside.netlify.app"})
    @Operation(summary = "상품 이미지 삭제", description = "특정 상품의 이미지를 삭제합니다.")
    @ApiResponse(responseCode = "200", description = "이미지 삭제 성공", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없음", content = @Content(mediaType = "application/json"))
    public ResponseEntity<?> deleteProductImages(
            @PathVariable Long id,
            @RequestParam List<String> imageUrlsToDelete  // 삭제할 상품 이미지 URL 리스트
    ) {
        // 상품 이미지 삭제 처리
        productService.deleteProductImages(id, imageUrlsToDelete);

        return ResponseEntity.ok("상품 이미지가 성공적으로 삭제되었습니다.");
    }

    // 상품 설명 이미지 삭제 (특정 설명 이미지 삭제)
    @DeleteMapping("/{id}/description-images")
    @CrossOrigin(origins = {"http://localhost:3000", "https://fit-inside.netlify.app"})
    @Operation(summary = "상품 설명 이미지 삭제", description = "특정 상품의 설명 이미지를 삭제합니다.")
    @ApiResponse(responseCode = "200", description = "설명 이미지 삭제 성공", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없음", content = @Content(mediaType = "application/json"))
    public ResponseEntity<?> deleteProductDescriptionImages(
            @PathVariable Long id,
            @RequestParam List<String> descImageUrlsToDelete  // 삭제할 설명 이미지 URL 리스트
    ) {
        // 상품 설명 이미지 삭제 처리
        productService.deleteProductDescriptionImages(id, descImageUrlsToDelete);

        return ResponseEntity.ok("상품 설명 이미지가 성공적으로 삭제되었습니다.");
    }



    // 상품 삭제 (관리자 전용, soft delete)
    @DeleteMapping("/{id}")
    @Operation(summary = "상품 삭제", description = "상품을 삭제합니다. (soft delete)")
    @ApiResponse(responseCode = "200", description = "상품 삭제 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponseDto.class)))
    @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없음", content = @Content(mediaType = "application/json"))
    public ResponseEntity<ProductResponseDto> deleteProduct(@PathVariable Long id) {
        ProductResponseDto deletedProduct = productService.deleteProduct(id);
        return ResponseEntity.ok(deletedProduct);
    }
}
