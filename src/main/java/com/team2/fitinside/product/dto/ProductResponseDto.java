package com.team2.fitinside.product.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ProductResponseDto {

    private Long id;                 // 상품 ID

    private String productName;      // 상품명

    private int price;               // 가격

    private String info;             // 상품 설명

    private int stock;               // 재고

    private Long userId;             // 판매자 ID

    private Long categoryId;         // 카테고리 ID

    private boolean isDeleted;       // 삭제 상태

    private LocalDateTime createdAt; // 생성 시간

    private LocalDateTime updatedAt; // 수정 시간

    private List<ProductImgDto> productImgs; // 상품 이미지 정보 리스트
}
