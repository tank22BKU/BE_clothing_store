package com.clothingstore.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Review")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
    
    @Id
    @Column(name = "ReviewID", length = 100)
    private String reviewId;
    
    @ManyToOne
    @JoinColumn(name = "ProductID", nullable = false)
    private Product product;
    
    @Column(name = "Comment", columnDefinition = "TEXT")
    private String comment;
    
    @Column(name = "Rating", nullable = false)
    private Integer rating;
    
    @Column(name = "ReviewDate", nullable = false)
    private LocalDateTime reviewDate;
    
    @OneToOne(mappedBy = "review", cascade = CascadeType.ALL)
    private SubmitReview submitReview;
}