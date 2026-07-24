package com.hotelhub.backend.auth.entity;

import com.hotelhub.backend.common.base.BaseEntity;
import com.hotelhub.backend.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "refresh_token",
        indexes = {
                @Index(name = "idx_refresh_token", columnList = "token"),
                @Index(name = "idx_refresh_user", columnList = "user")
        }

)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshToken extends BaseEntity {
    // gia tri RefreshToken
    @Column(nullable = false, unique = true, length = 512)
    private String token;
    // Ngay het han
    @Column(nullable = false)
    private LocalDateTime expiryDate;
    // Token bi thu hoi
    @Builder.Default
    @Column(nullable = false)
    private Boolean revoked = false;
    // chu so huu
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;
}
