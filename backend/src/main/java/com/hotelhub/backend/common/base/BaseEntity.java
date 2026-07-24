package com.hotelhub.backend.common.base;

import com.hotelhub.backend.config.JpaConfig.JpaAuditConfig;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    // khai báo ở dạng trìu tượng
    // không thể tạo trực tiếp 1 đối tượng BaseEntity  mà phải thông qua các class con kế thừa
    @Id
    @GeneratedValue
    private UUID id;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private Boolean deleted = false;


    // @MappedSuperclass class cha cho các class con kế thừa
    // Tất cả các cột trong class đều được kế thừa cho các class con

}
