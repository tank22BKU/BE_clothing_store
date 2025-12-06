package com.clothingstore.entity;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "PurchaseOrderDetails")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(PurchaseOrderDetails.PurchaseOrderDetailsId.class)
public class PurchaseOrderDetails {
    
    @Id
    @ManyToOne
    @JoinColumn(name = "PurchaseOrderID")
    private PurchaseOrder purchaseOrder;
    
    @Id
    @Column(name = "Date")
    private LocalDateTime date;
    
    @Column(name = "ImportPrice", precision = 12, scale = 2, nullable = false)
    private BigDecimal importPrice;
    
    @Column(name = "Quantity", nullable = false)
    private Integer quantity;
    
    @Column(name = "ProductID", length = 100, nullable = false)
    private String productId;
    
    @Column(name = "VariantID", length = 100, nullable = false)
    private String variantId;
    
    @OneToMany(mappedBy = "purchaseOrderDetails", cascade = CascadeType.ALL)
    private Set<PriceQuote> priceQuotes;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PurchaseOrderDetailsId implements Serializable {
        private String purchaseOrder;
        private LocalDateTime date;
    }
}