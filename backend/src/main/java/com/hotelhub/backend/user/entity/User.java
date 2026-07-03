package com.hotelhub.backend.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;



    @Column(name = "cccd_number", unique = true)
    private String cccdNumber;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private String fullName;
    @Column(nullable = false)
    private String email;

    private String phoneNumber;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private String status;


}
