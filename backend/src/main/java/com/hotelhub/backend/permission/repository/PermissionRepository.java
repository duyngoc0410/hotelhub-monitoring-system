package com.hotelhub.backend.permission.repository;

import com.hotelhub.backend.common.constant.enums.PermissionType;
import com.hotelhub.backend.permission.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, UUID> {
    Optional<Permission> findByName(PermissionType name);
    boolean existsByName(PermissionType name);
}
