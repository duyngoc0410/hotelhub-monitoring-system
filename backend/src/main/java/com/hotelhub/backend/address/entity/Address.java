package com.hotelhub.backend.address.entity;

import com.hotelhub.backend.common.base.BaseEntity;
import com.hotelhub.backend.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "addresses",
        indexes = {
                @Index(name = "idx_address_user", columnList = "user_id"),
//                @Index(name = "idx_address_hotel", columnList = "hotel_id"),
                @Index(name = "idx_address_province", columnList = "province"),
                @Index(name = "idx_address_district", columnList = "district")
        }
)
@Builder
public class Address extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String country;

    @Column(nullable = false, length = 100)
    private String province;

    @Column(nullable = false, length = 100)
    private String district;

    @Column(nullable = false, length = 100)
    private String ward;

    @Column(nullable = false, length = 100)
    private String street;

    @Column(length = 20)
    private String postalCode;

    // latitude chi su dung cho hotel , user de null
    private Double latitude;

    // longitude chi su dung cho hotel , user de null
    private Double longitude;

    @Builder.Default
    private Boolean isDefault = false;

    // Quan he 1 User -> N Address
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // Quan he 1 hotel -> 1 Address
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "hotel_id")
////    private Hotel





}
