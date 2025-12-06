package com.clothingstore.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "Customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
    
    @Id
    @Column(name = "CustomerID", length = 100)
    private String customerId;
    
    @OneToOne
    @MapsId
    @JoinColumn(name = "CustomerID")
    private User user;
    
    @Column(name = "RegistrationDate", nullable = false)
    private LocalDate registrationDate;
    
    @ElementCollection
    @CollectionTable(name = "Customer_Address", joinColumns = @JoinColumn(name = "CustomerID"))
    @Column(name = "Address", length = 255)
    private Set<String> addresses;
}