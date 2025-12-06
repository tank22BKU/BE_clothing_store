package com.clothingstore.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "Product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    
    @Id
    @Column(name = "ProductID", length = 100)
    private String productId;
    
    @Column(name = "Name", length = 255, nullable = false)
    private String name;
    
    @Column(name = "Description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "DefaultPrice", precision = 12, scale = 2, nullable = false)
    private BigDecimal defaultPrice;
    
    @Column(name = "NumberOfRating", nullable = false)
    private Integer numberOfRating = 0;
    
    @Column(name = "AverageOfRating", precision = 3, scale = 2, nullable = false)
    private BigDecimal averageOfRating = BigDecimal.ZERO;
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<ProductVariant> variants;
    
    @ElementCollection
    @CollectionTable(name = "Product_Image", joinColumns = @JoinColumn(name = "ProductID"))
    @Column(name = "ProductImage", length = 255)
    private Set<String> images;
    
    @ManyToMany
    @JoinTable(
        name = "Product_Category",
        joinColumns = @JoinColumn(name = "ProductID"),
        inverseJoinColumns = @JoinColumn(name = "CategoryID")
    )
    private Set<Category> categories;
    
    @ManyToMany(mappedBy = "products")
    private Set<Promotion> promotions;
}