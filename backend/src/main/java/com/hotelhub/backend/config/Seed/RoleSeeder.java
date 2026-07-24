package com.hotelhub.backend.config.Seed;

import com.hotelhub.backend.common.constant.enums.RoleType;
import com.hotelhub.backend.permission.entity.Permission;
import com.hotelhub.backend.permission.repository.PermissionRepository;
import com.hotelhub.backend.role.entity.Role;
import com.hotelhub.backend.role.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class RoleSeeder {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;


    @Transactional
    public void seed() {
        if (roleRepository.existsByName(RoleType.ADMIN)){
           return;
        }
        Set<Permission> allPermissions =
            new HashSet<>(permissionRepository.findAll());
        Role admin = Role.builder()
                .name(RoleType.ADMIN)
                .permissions(allPermissions)
                .build();

        roleRepository.save(admin);
    }
}
