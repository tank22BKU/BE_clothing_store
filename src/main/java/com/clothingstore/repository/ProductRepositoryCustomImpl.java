package com.clothingstore.repository;

import com.clothingstore.dto.response.ProductDetailsResponse;
import com.clothingstore.entity.Product;
import com.clothingstore.entity.ProductVariant;
import com.clothingstore.entity.QProduct;
import com.clothingstore.entity.QProductVariant;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ProductRepositoryCustomImpl {

    private final JPAQueryFactory queryFactory;

    public List<ProductDetailsResponse> getAllProductDetails() {
        QProduct product = QProduct.product;
        QProductVariant variant = QProductVariant.productVariant;

        List<ProductDetailsResponse> results = new ArrayList<>();

        // Query để lấy product và variant với fetch join images
        List<Product> products = queryFactory
                .selectFrom(product)
                .distinct()
                .leftJoin(product.images).fetchJoin()
                .fetch();

        // Lấy tất cả variants
        List<ProductVariant> variants = queryFactory
                .selectFrom(variant)
                .fetch();

        // Map variants theo productId
        for (Product productEntity : products) {
            // Lọc variants của product này
            List<ProductVariant> productVariants = variants.stream()
                    .filter(v -> v.getProduct().getProductId().equals(productEntity.getProductId()))
                    .toList();

            Set<String> images = productEntity.getImages();

            if (productVariants.isEmpty()) {
                // Không có variant - sử dụng default price
                if (images.isEmpty()) {
                    // Nếu không có ảnh, tạo 1 response với null imageURL
                    results.add(new ProductDetailsResponse(
                            productEntity.getProductId(),
                            null,
                            productEntity.getName(),
                            null,
                            null,
                            productEntity.getDefaultPrice(),
                            productEntity.getDescription()
                    ));
                } else {
                    for (String imageUrl : images) {
                        results.add(new ProductDetailsResponse(
                                productEntity.getProductId(),
                                imageUrl,
                                productEntity.getName(),
                                null,
                                null,
                                productEntity.getDefaultPrice(),
                                productEntity.getDescription()
                        ));
                    }
                }
            } else {
                // Có variants
                for (ProductVariant variantEntity : productVariants) {
                    if (images.isEmpty()) {
                        // Nếu không có ảnh
                        results.add(new ProductDetailsResponse(
                                productEntity.getProductId(),
                                null,
                                productEntity.getName(),
                                variantEntity.getSize(),
                                variantEntity.getColor(),
                                variantEntity.getPrice(),
                                productEntity.getDescription()
                        ));
                    } else {
                        // Mỗi variant kết hợp với mỗi ảnh
                        for (String imageUrl : images) {
                            results.add(new ProductDetailsResponse(
                                    productEntity.getProductId(),
                                    imageUrl,
                                    productEntity.getName(),
                                    variantEntity.getSize(),
                                    variantEntity.getColor(),
                                    variantEntity.getPrice(),
                                    productEntity.getDescription()
                            ));
                        }
                    }
                }
            }
        }

        return results;
    }
}
