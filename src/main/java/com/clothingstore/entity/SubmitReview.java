package com.clothingstore.entity;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Table(name = "Submit_Review")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(SubmitReview.SubmitReviewId.class)
public class SubmitReview {
    
    @Id
    @OneToOne
    @JoinColumn(name = "ReviewID")
    private Review review;
    
    @Id
    @ManyToOne
    @JoinColumn(name = "ProductID")
    private Product product;
    
    @Id
    @ManyToOne
    @JoinColumn(name = "CustomerID")
    private Customer customer;
    
    @Id
    @ManyToOne
    @JoinColumn(name = "OrderID")
    private Order order;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SubmitReviewId implements Serializable {
        private String review;
        private String product;
        private String customer;
        private String order;
    }
}