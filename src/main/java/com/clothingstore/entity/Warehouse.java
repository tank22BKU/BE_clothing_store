package com.clothingstore.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@Table(name = "Warehouse")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Warehouse {
    
    @Id
    @Column(name = "WarehouseID", length = 100)
    private String warehouseId;
    
    @Column(name = "Capacity", nullable = false)
    private Integer capacity;
    
    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL)
    private Set<Location> locations;
    
    @ManyToMany
    @JoinTable(
        name = "Employee_Warehouse",
        joinColumns = @JoinColumn(name = "WarehouseID"),
        inverseJoinColumns = @JoinColumn(name = "EmployeeID")
    )
    private Set<Employee> employees;
    
    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL)
    private Set<WarehouseVariant> warehouseVariants;
}