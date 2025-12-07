package com.clothingstore.repository;

import com.clothingstore.entity.Category;
import com.clothingstore.entity.Category.CategoryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

    List<Category> findByStatus(CategoryStatus status);

    boolean existsByCategoryIdAndStatus(String categoryId, CategoryStatus status);
}