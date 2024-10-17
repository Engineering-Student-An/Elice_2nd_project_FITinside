package com.team2.fitinside.product.mapper;

import com.team2.fitinside.product.dto.*;
import com.team2.fitinside.product.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    Product toEntity(ProductCreateDto productCreateDto);

    Product toEntity(Long id, ProductUpdateDto productUpdateDto);

    @Mapping(source = "category.id", target = "categoryId") // category의 ID를 categoryId로 매핑
    ProductResponseDto toDto(Product product);

    @Mapping(target = "productImgUrls", ignore = true)
    ProductCreateDto toProductCreateDto(ProductInsertDto productInsertDto);

    @Mapping(target = "productImgUrls", ignore = true)
    ProductUpdateDto toProductUpdateDto(ProductInsertDto productInsertDto);
}
