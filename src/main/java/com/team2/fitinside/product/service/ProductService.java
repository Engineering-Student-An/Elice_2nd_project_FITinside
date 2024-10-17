package com.team2.fitinside.product.service;

import com.team2.fitinside.product.dto.ProductCreateDto;
import com.team2.fitinside.product.dto.ProductResponseDto;
import com.team2.fitinside.product.dto.ProductUpdateDto;
import com.team2.fitinside.product.entity.Product;
import com.team2.fitinside.global.exception.CustomException;
import com.team2.fitinside.global.exception.ErrorCode;
import com.team2.fitinside.product.image.S3ImageService;
import com.team2.fitinside.product.mapper.ProductMapper;
import com.team2.fitinside.product.repository.ProductRepository;
import com.team2.fitinside.category.repository.CategoryRepository;
import com.team2.fitinside.category.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final S3ImageService s3ImageService;
    private final String DEFAULT_IMAGE_URL = "https://dummyimage.com/100x100";

    // 페이지네이션, 정렬, 검색을 적용한 상품 전체 목록 조회
    public Page<ProductResponseDto> getAllProducts(int page, int size, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equalsIgnoreCase("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        if (keyword != null && !keyword.isEmpty()) {
            return productRepository.searchByKeywordAndIsDeletedFalse(keyword, pageable)
                    .map(ProductMapper.INSTANCE::toDto);
        } else {
            return productRepository.findByIsDeletedFalse(pageable)
                    .map(ProductMapper.INSTANCE::toDto);
        }
    }

    // 페이지네이션, 정렬, 검색을 적용한 카테고리별 상품 목록 조회
    public Page<ProductResponseDto> getProductsByCategory(Long categoryId, int page, int size, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equalsIgnoreCase("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));

        if (keyword != null && !keyword.isEmpty()) {
            return productRepository.searchByKeywordAndCategoryAndIsDeletedFalse(category, keyword, pageable)
                    .map(ProductMapper.INSTANCE::toDto);
        } else {
            return productRepository.findByCategoryAndIsDeletedFalse(category, pageable)
                    .map(ProductMapper.INSTANCE::toDto);
        }
    }

    // 상품 상세 조회
    public ProductResponseDto findProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        return ProductMapper.INSTANCE.toDto(product);
    }

    // 상품 등록 (이미지 업로드 포함)
//    @Transactional
//    public ProductResponseDto createProduct(ProductCreateDto productCreateDto, List<MultipartFile> images) {
//        Product product = ProductMapper.INSTANCE.toEntity(productCreateDto);
//
//        Category category = categoryRepository.findById(productCreateDto.getCategoryId())
//                .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));
//        product.setCategory(category);
//
//        // S3 이미지 업로드 처리 (이미지 없으면 빈 리스트로 처리)
//        List<String> imageUrls = uploadImages(images);
//
//        // 이미지가 없을 경우 기본 더미 이미지 추가
//        if (imageUrls.isEmpty()) {
//            imageUrls.add(DEFAULT_IMAGE_URL);
//        }
//
//        product.setProductImgUrls(imageUrls);
//        Product savedProduct = productRepository.save(product);
//
//        return ProductMapper.INSTANCE.toDto(savedProduct);
//    }


    // 상품 등록 (이미지 업로드 포함)
//    @Transactional
//    public ProductResponseDto createProduct(ProductCreateDto productCreateDto, List<MultipartFile> images) {
//        // 카테고리 이름을 통해 카테고리 조회
//        Category category = categoryRepository.findByName(productCreateDto.getCategoryName())
//                .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));
//
//        // ProductCreateDto를 Entity로 변환
//        Product product = ProductMapper.INSTANCE.toEntity(productCreateDto);
//        product.setCategory(category);
//
//        // S3 이미지 업로드 처리
//        List<String> imageUrls = uploadImages(images);
//        if (imageUrls.isEmpty()) {
//            imageUrls.add(DEFAULT_IMAGE_URL); // 이미지가 없을 경우 기본 더미 이미지 추가
//        }
//
//        product.setProductImgUrls(imageUrls);
//        Product savedProduct = productRepository.save(product);
//
//        return ProductMapper.INSTANCE.toDto(savedProduct);
//    }

//    @Transactional
//    public ProductResponseDto createProduct(ProductCreateDto productCreateDto, List<MultipartFile> images) {
//        // 여러 개의 카테고리를 처리
//        List<Category> categories = productCreateDto.getCategories();
//
//        // 등록할 카테고리가 존재하는지 확인하고 카테고리 리스트를 설정
//        if (categories == null || categories.isEmpty()) {
//            throw new CustomException(ErrorCode.CATEGORY_NOT_FOUND);
//        }
//
//        Product product = ProductMapper.INSTANCE.toEntity(productCreateDto);
//        product.setCategories(categories); // Product에 카테고리 리스트를 설정
//
//        // S3 이미지 업로드 처리
//        List<String> imageUrls = uploadImages(images);
//
//        // 이미지가 없을 경우 기본 더미 이미지 추가
//        if (imageUrls.isEmpty()) {
//            imageUrls.add(DEFAULT_IMAGE_URL);
//        }
//
//        product.setProductImgUrls(imageUrls);
//        Product savedProduct = productRepository.save(product);
//
//        return ProductMapper.INSTANCE.toDto(savedProduct);
//    }

//    @Transactional
//    public ProductResponseDto createProduct(ProductCreateDto productCreateDto, List<MultipartFile> images) {
//        // ProductMapper를 사용해 문자열로 받은 카테고리명을 Category 엔티티로 변환
//        Product product = ProductMapper.INSTANCE.toEntity(productCreateDto, categoryRepository);
//
//        // S3 이미지 업로드 처리
//        List<String> imageUrls = uploadImages(images);
//
//        // 이미지가 없을 경우 기본 더미 이미지 추가
//        if (imageUrls.isEmpty()) {
//            imageUrls.add(DEFAULT_IMAGE_URL);
//        }
//
//        product.setProductImgUrls(imageUrls);
//        Product savedProduct = productRepository.save(product);
//
//        return ProductMapper.INSTANCE.toDto(savedProduct);
//    }

    @Transactional
    public ProductResponseDto createProduct(ProductCreateDto productCreateDto, List<MultipartFile> images) {
        Product product = ProductMapper.INSTANCE.toEntity(productCreateDto, categoryRepository);

        // S3 이미지 업로드 처리
        List<String> imageUrls = uploadImages(images);

        // 이미지가 없을 경우 기본 더미 이미지 추가
        if (imageUrls.isEmpty()) {
            imageUrls.add(DEFAULT_IMAGE_URL);
        }

        product.setProductImgUrls(imageUrls);
        Product savedProduct = productRepository.save(product);

        return ProductMapper.INSTANCE.toDto(savedProduct);
    }





    // 상품 수정 (이미지 업로드 포함)
//    @Transactional
//    public ProductResponseDto updateProduct(Long id, ProductUpdateDto productUpdateDto, List<MultipartFile> images) {
//        Product existingProduct = productRepository.findById(id)
//                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
//
//        Product updatedProduct = ProductMapper.INSTANCE.toEntity(id, productUpdateDto);
//
//        Category category = categoryRepository.findById(productUpdateDto.getCategoryId())
//                .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));
//        updatedProduct.setCategory(category);
//
//        // S3 이미지 업데이트 처리
//        List<String> imageUrls = uploadImages(images);
//
//        // 이미지가 없을 경우 기본 더미 이미지 추가
//        if (imageUrls.isEmpty()) {
//            imageUrls.add(DEFAULT_IMAGE_URL);
//        }
//
//        updatedProduct.setProductImgUrls(imageUrls);
//        productRepository.save(updatedProduct);
//
//        return ProductMapper.INSTANCE.toDto(updatedProduct);
//    }

//    @Transactional
//    public ProductResponseDto updateProduct(Long id, ProductUpdateDto productUpdateDto, List<MultipartFile> images) {
//        // 기존 상품 엔티티 조회
//        Product existingProduct = productRepository.findById(id)
//                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
//
//        // 카테고리 업데이트 처리
//        Category category = categoryRepository.findById(productUpdateDto.getCategoryId())
//                .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));
//        existingProduct.setCategory(category);
//
//        // 상품 정보 업데이트 (기존 엔티티에 새로운 정보 반영)
//        ProductMapper.INSTANCE.updateProductFromDto(productUpdateDto, existingProduct);
//
//        // S3 이미지 업데이트 처리
//        List<String> imageUrls = uploadImages(images);
//
//        // 이미지가 없을 경우 기본 더미 이미지 추가
//        if (imageUrls.isEmpty()) {
//            imageUrls.add(DEFAULT_IMAGE_URL);
//        }
//
//        existingProduct.setProductImgUrls(imageUrls);
//
//        // 업데이트된 상품 엔티티 저장
//        productRepository.save(existingProduct);
//
//        // DTO로 변환하여 반환
//        return ProductMapper.INSTANCE.toDto(existingProduct);
//    }

//    @Transactional
//    public ProductResponseDto updateProduct(Long id, ProductUpdateDto productUpdateDto, List<MultipartFile> images) {
//        Product existingProduct = productRepository.findById(id)
//                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
//
//        List<Category> categories = categoryRepository.findByNameIn(productUpdateDto.getCategories())
//                .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));
//
//        Product updatedProduct = ProductMapper.INSTANCE.toEntity(id, productUpdateDto);
//        updatedProduct.setCategories(categories);
//
//        List<String> imageUrls = uploadImages(images);
//        if (imageUrls.isEmpty()) {
//            imageUrls.add(DEFAULT_IMAGE_URL);
//        }
//
//        updatedProduct.setProductImgUrls(imageUrls);
//        productRepository.save(updatedProduct);
//
//        return ProductMapper.INSTANCE.toDto(updatedProduct);
//    }

    @Transactional
    public ProductResponseDto updateProduct(Long id, ProductUpdateDto productUpdateDto, List<MultipartFile> images) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        // 카테고리 매핑
        Product updatedProduct = ProductMapper.INSTANCE.toEntity(id, productUpdateDto, categoryRepository);

        // 이미지 업데이트 처리
        List<String> imageUrls = uploadImages(images);
        if (imageUrls.isEmpty()) {
            imageUrls.add(DEFAULT_IMAGE_URL);
        }

        updatedProduct.setProductImgUrls(imageUrls);
        productRepository.save(updatedProduct);

        return ProductMapper.INSTANCE.toDto(updatedProduct);
    }




    // 이미지 업로드 처리 메서드 (S3 업로드)
    private List<String> uploadImages(List<MultipartFile> images) {
        List<String> imageUrls = new ArrayList<>();

        // images가 null이 아니고 빈 값이 아닌 경우에만 처리
        if (images != null && !images.isEmpty()) {
            for (MultipartFile image : images) {
                String imageUrl = s3ImageService.upload(image); // S3에 업로드하고 URL 받기
                imageUrls.add(imageUrl);
            }
        }

        return imageUrls;
    }

    // 상품 삭제 (soft delete)
    @Transactional
    public ProductResponseDto deleteProduct(Long id) {
        Product deletedProduct = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
        deletedProduct.setIsDeleted(true);
        productRepository.save(deletedProduct);
        return ProductMapper.INSTANCE.toDto(deletedProduct);
    }
}
