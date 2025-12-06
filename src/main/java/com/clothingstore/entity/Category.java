package com.clothingstore.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@Table(name = "Category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
    
    @Id
    @Column(name = "CategoryID", length = 100)
    private String categoryId;
    
    @Column(name = "Name", length = 255, nullable = false)
    private String name;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    private CategoryStatus status;
    
    @ManyToMany(mappedBy = "categories")
    private Set<Product> products;
    
    public enum CategoryStatus {
        ACTIVE, INACTIVE
    }
}