package com.clothingstore.entity;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.clothingstore.entity.PurchaseOrderDetails;

@Entity
@Table(name = "Price_Quote")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(PriceQuote.PriceQuoteId.class)
public class PriceQuote {

    @Id
    @Column(name = "QuoteID")
    private String quoteId;

    @Id
    @Column(name = "Date")
    private LocalDateTime date;

    @Id
    @Column(name = "PurchaseOrderID")
    private String purchaseOrderID;   // MATCH IdClass

    @Id
    @Column(name = "PurchaseOrderDate")
    private LocalDateTime purchaseOrderDate; // MATCH IdClass

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "PurchaseOrderID", referencedColumnName = "PurchaseOrderID", insertable = false, updatable = false),
        @JoinColumn(name = "PurchaseOrderDate", referencedColumnName = "Date", insertable = false, updatable = false)
    })
    private PurchaseOrderDetails purchaseOrderDetails;

    @Column(name = "QuoteMonth", nullable = false)
    private Integer quoteMonth;

    @Column(name = "QuoteValue", precision = 12, scale = 2, nullable = false)
    private BigDecimal quoteValue;

    @ManyToOne
    @JoinColumn(name = "SupplierID", nullable = false)
    private Supplier supplier;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PriceQuoteId implements Serializable {

        private String quoteId;
        private LocalDateTime date;
        private String purchaseOrderID;  // FK part 1
        private LocalDateTime purchaseOrderDate;  // FK part 2
    }
}


                