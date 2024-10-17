package com.team2.fitinside.product.entity;

import com.team2.fitinside.category.entity.Category;
import jakarta.persistence.*;

@Entity
public class CategoryProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}