package com.hotelhub.backend.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Users {
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
    private String status;

}
