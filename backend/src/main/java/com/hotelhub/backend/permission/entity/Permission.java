package com.hotelhub.backend.permission.entity;

import com.hotelhub.backend.common.base.BaseEntity;
import com.hotelhub.backend.common.constant.enums.PermissionType;
import com.hotelhub.backend.role.entity.Role;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "permissions")
public class Permission extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private PermissionType name;

    private String description;

    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles = new HashSet<>();
}
