package com.hotelhub.backend.config.Seed;

import com.hotelhub.backend.common.constant.enums.Gender;
import com.hotelhub.backend.common.constant.enums.RoleType;
import com.hotelhub.backend.common.constant.enums.UserStatus;
import com.hotelhub.backend.role.entity.Role;
import com.hotelhub.backend.role.repository.RoleRepository;
import com.hotelhub.backend.user.entity.User;
import com.hotelhub.backend.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminSeeder {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void seed() {
        String adminCccd = "000000000001";

        if (userRepository.existsByCccdNumber(adminCccd)) {
            return;
        }

        Role adminRole = roleRepository.findByName(RoleType.ADMIN)
                .orElseThrow(() ->
                        new IllegalStateException("ADMIN role not found")
                );

        User admin = User.builder()
                .fullName("System Administrator")
                .cccdNumber(adminCccd)
                .email("admin@hotelhub.com")
                .passwordHash(passwordEncoder.encode("Admin@123"))
                .phoneNumber("0000000000")
                .gender(Gender.OTHER)
                .userStatus(UserStatus.ACTIVE)
                .role(adminRole)
                .build();
        userRepository.save(admin);

    }
}
