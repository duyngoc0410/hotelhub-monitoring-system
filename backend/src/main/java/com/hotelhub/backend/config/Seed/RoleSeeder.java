package com.hotelhub.backend.config.Seed;

import com.hotelhub.backend.common.constant.enums.RoleType;
import com.hotelhub.backend.permission.entity.Permission;
import com.hotelhub.backend.permission.repository.PermissionRepository;
import com.hotelhub.backend.role.entity.Role;
import com.hotelhub.backend.role.repository.RoleRepository;
import jakarta.transaction.Transactional;

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

        Set<Permission> allPermissions =
                new HashSet<>(permissionRepository.findAll());

        // ADMIN
        if (!roleRepository.existsByName(RoleType.ADMIN)) {
            Role admin = Role.builder()
                    .name(RoleType.ADMIN)
                    .description("System Administrator")
                    .permissions(allPermissions)
                    .build();

            roleRepository.save(admin);
        }

        // OWNER
        if (!roleRepository.existsByName(RoleType.OWNER)) {
            Role owner = Role.builder()
                    .name(RoleType.OWNER)
                    .description("Hotel Owner")
                    .build();

            roleRepository.save(owner);
        }

        // STAFF
        if (!roleRepository.existsByName(RoleType.STAFF)) {
            Role staff = Role.builder()
                    .name(RoleType.STAFF)
                    .description("Hotel Staff")
                    .build();

            roleRepository.save(staff);
        }

        // CUSTOMER
        if (!roleRepository.existsByName(RoleType.CUSTOMER)) {
            Role customer = Role.builder()
                    .name(RoleType.CUSTOMER)
                    .description("Hotel Customer")
                    .build();

            roleRepository.save(customer);
        }
    }
}
