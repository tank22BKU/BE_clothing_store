package com.clothingstore.entity;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Table(name = "Supplier_Contact")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(SupplierContact.SupplierContactId.class)
public class SupplierContact {
    
    @Id
    @ManyToOne
    @JoinColumn(name = "SupplierID")
    private Supplier supplier;
    
    @Id
    @Column(name = "SaleDepartmentNum", length = 50)
    private String saleDepartmentNum;
    
    @Id
    @Column(name = "SalePersonNum", length = 50)
    private String salePersonNum;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SupplierContactId implements Serializable {
        private String supplier;
        private String saleDepartmentNum;
        private String salePersonNum;
    }
}