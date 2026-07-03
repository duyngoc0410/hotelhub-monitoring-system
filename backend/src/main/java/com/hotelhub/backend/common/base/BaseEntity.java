package com.hotelhub.backend.common.base;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {
    // khai báo ở dạng trìu tượng
    // không thể tạo trực tiếp 1 đối tượng BaseEntity  mà phải thông qua các class con kế thừa
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // @MappedSuperclass class cha cho các class con kế thừa
    // Tất cả các cột trong class đều được kế thừa cho các class con

}
