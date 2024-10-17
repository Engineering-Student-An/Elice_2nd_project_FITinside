package com.team2.fitinside.product.dto;

import com.team2.fitinside.category.entity.Category;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.ToString;
import java.util.List;

@Getter
@Setter
@ToString
public class ProductUpdateDto {

    private Long categoryId;
    private List<String> categories;
    private String productName;
    private Integer price;
    private String info;
    private Integer stock;
    private String manufacturer;
    private List<String> productImgUrls;

}
