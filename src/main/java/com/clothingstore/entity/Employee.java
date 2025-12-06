package com.clothingstore.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "Employee")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {
    
    @Id
    @Column(name = "EmployeeID", length = 100)
    private String employeeId;
    
    @OneToOne
    @MapsId
    @JoinColumn(name = "EmployeeID")
    private User user;
    
    @Column(name = "Salary", precision = 12, scale = 2, nullable = false)
    private BigDecimal salary;
    
    @Column(name = "Position", length = 100, nullable = false)
    private String position;
    
    @ManyToOne
    @JoinColumn(name = "SupervisorID")
    private Employee supervisor;
    
    @OneToMany(mappedBy = "supervisor")
    private Set<Employee> subordinates;
    
    @ManyToMany(mappedBy = "employees")
    private Set<Warehouse> warehouses;
}