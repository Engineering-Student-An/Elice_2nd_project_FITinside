package com.team2.fitinside.product.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Getter
@Setter
public class ProductCreateDto {

    @NotNull(message = "카테고리 ID는 필수 입력 값입니다.")
    private Long categoryId;

    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    private String productName;

    @NotNull(message = "가격은 필수 입력 값입니다.")
    private Integer price;

    @NotBlank(message = "상품 설명은 필수 입력 값입니다.")
    private String info;

    @NotNull(message = "재고는 필수 입력 값입니다.")
    private Integer stock;

    @Column(name = "manufacturer", length = 100)
    private String manufacturer;

//    @NotNull(message = "판매자 ID는 필수 입력 값입니다.")
//    private Long userId;

    @NotNull(message = "이미지 URL 목록은 필수 입력 값입니다.")
    @Size(min = 1, message = "최소 하나 이상의 이미지 URL이 필요합니다.")
    private List<String> productImgUrls;

}