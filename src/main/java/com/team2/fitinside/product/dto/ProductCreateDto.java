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

    private Long categoryId;
    private String productName;
    private Integer price;
    private String info;
    private Integer stock;
    private String manufacturer;
    private List<String> productImgUrls;

}
