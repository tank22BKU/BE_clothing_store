package com.clothingstore.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "User")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @Column(name = "UserID", length = 100)
    private String userId;

    @Column(name = "Phone", length = 20, nullable = false, unique = true)
    private String phone;

    @Column(name = "Email", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "Password", length = 255, nullable = false)
    private String password;

    @Column(name = "FirstName", length = 50, nullable = false)
    private String firstName;

    @Column(name = "LastName", length = 50, nullable = false)
    private String lastName;
}