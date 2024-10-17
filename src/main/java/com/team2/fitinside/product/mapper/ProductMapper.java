package com.team2.fitinside.product.mapper;

import com.team2.fitinside.category.entity.Category;
import com.team2.fitinside.category.repository.CategoryRepository;
import com.team2.fitinside.global.exception.CustomException;
import com.team2.fitinside.global.exception.ErrorCode;
import com.team2.fitinside.product.dto.*;
import com.team2.fitinside.product.entity.Product;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.amazonaws.services.ec2.model.LaunchTemplateHttpTokensState.Optional;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    // 카테고리 문자열 리스트 -> 카테고리 엔티티 리스트 변환
    @Mapping(target = "categories", source = "categories", qualifiedByName = "mapCategoryNamesToEntities")
    Product toEntity(ProductCreateDto productCreateDto, @Context CategoryRepository categoryRepository);

    @Mapping(target = "categories", source = "categories", qualifiedByName = "mapCategoryNamesToEntities")
    Product toEntity(Long id, ProductUpdateDto productUpdateDto, @Context CategoryRepository categoryRepository);

    // DTO -> 엔티티 변환 시 매핑 규칙 정의
    @Mapping(source = "id", target = "id")
    ProductResponseDto toDto(Product product);

    // ProductInsertDto -> ProductCreateDto 변환
    @Mapping(target = "productImgUrls", ignore = true)
    ProductCreateDto toProductCreateDto(ProductInsertDto productInsertDto);

    @Mapping(target = "productImgUrls", ignore = true)
    ProductUpdateDto toProductUpdateDto(ProductInsertDto productInsertDto);

    // 카테고리 이름을 엔티티로 변환하는 메서드
    @Named("mapCategoryNamesToEntities")
    default List<Category> mapCategoryNamesToEntities(List<String> categoryNames, @Context CategoryRepository categoryRepository) {
        if (categoryNames == null || categoryNames.isEmpty()) {
            return new ArrayList<>();
        }
        return categoryNames.stream()
                .map(name -> categoryRepository.findByNameAndIsDeletedFalse(name)
                        .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND)))
                .collect(Collectors.toList());

    }
}

