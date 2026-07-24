package com.hotelhub.backend.config.Seed;

import com.hotelhub.backend.common.constant.enums.PermissionType;
import com.hotelhub.backend.permission.entity.Permission;
import com.hotelhub.backend.permission.repository.PermissionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PermissionSeeder {

    private final PermissionRepository permissionRepository;

    @Transactional
    public void seed() {
        for (PermissionType permissionType : PermissionType.values()){
            if (permissionRepository.existsByName(permissionType)){
                continue;
            }
            Permission permission = Permission.builder()
                    .name(permissionType)
                    .build();

            permissionRepository.save(permission);
        }

    }
}
