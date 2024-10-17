package com.team2.fitinside.category.entity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
//@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", nullable = false)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    private Long displayOrder;

    // Soft delete를 위한 필드
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @JsonManagedReference // 순환 참조 방지
    private Category parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // 순환 참조 방지
    private List<Category> children = new ArrayList<>();

    // 이미지 URL 필드 추가
    @Column(name = "image_url")
    private String imageUrl;

    // 삭제 시 isDeleted를 true로 설정하는 메서드
    public void delete() {
        this.isDeleted = true;
    }

    public void updateCategory(String name, Long displayOrder, Category parent, String imageUrl) {
        this.name = name;
        this.displayOrder = displayOrder;
        this.parent = parent;
        this.imageUrl = imageUrl; // 이미지 URL 업데이트
    }


    // 하위 카테고리를 추가하는 메서드
    public void addChildCategory(Category child) {
        child.parent = this;
        this.children.add(child);
    }

    // 하위 카테고리를 제거하는 메서드
    public void removeChildCategory(Category child) {
        this.children.remove(child);
        child.parent = null;
    }
}

