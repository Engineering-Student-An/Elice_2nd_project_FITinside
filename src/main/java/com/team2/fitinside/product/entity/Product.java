package com.team2.fitinside.product.entity;


import com.team2.fitinside.category.entity.Category;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
@Table(name = "product")
@EntityListeners(AuditingEntityListener.class)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    // 카테고리와의 다대일 관계 (ManyToOne)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "product_name", length = 100, nullable = false)
    private String productName;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "info", length = 500)
    private String info;

    //재고수량
    @Column(name = "product_stock", nullable = false)
    private int stock;

    // 제조사
    @Column(name = "manufacturer", length = 100)
    private String manufacturer;

    // 이미지 ID 목록을 저장하는 필드
    @ElementCollection
    @CollectionTable(name = "product_img_urls", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "product_img_url")
    private List<String> productImgUrls = new ArrayList<>();

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @PrePersist
    public void prePersist() {
        this.isDeleted = false;
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // 카테고리를 설정하는 메서드
    public void setCategory(Category category) {
        this.category = category;
    }

    // 삭제 상태 설정 메서드
    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    // 이미지 URL 설정 메서드
    public void setProductImgUrls(List<String> productImgUrls) {
        this.productImgUrls = productImgUrls;
    }

    // 주문 시 재고 변동
    public void sold(int count){
        this.stock -= count;
    }

}