package com.hotelhub.backend.auth.service;

import com.hotelhub.backend.auth.dto.reponse.AuthResponse;
import com.hotelhub.backend.auth.dto.request.RegisterRequest;
import com.hotelhub.backend.common.constant.enums.RoleType;
import com.hotelhub.backend.common.constant.enums.UserStatus;
import com.hotelhub.backend.role.entity.Role;
import com.hotelhub.backend.role.repository.RoleRepository;
import com.hotelhub.backend.user.entity.User;

import com.hotelhub.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Transactional
    public AuthResponse register(RegisterRequest registerRequest){
        // Kiểm Tra Email
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email already in use");
        }
        // Kiểm Tra Phone
        if (userRepository.existsByPhoneNumber(
                registerRequest.getPhoneNumber())) {

            throw new RuntimeException("Phone number already in use");
        }
        // Kiểm tra cccd
        if (registerRequest.getCccdNumber() != null
                && !registerRequest.getCccdNumber().isBlank()
                && userRepository.existsByCccdNumber(
                registerRequest.getCccdNumber())) {

            throw new RuntimeException("CCCD already in use");
        }
        // 2. Lấy Role CUSTOMER
        Role customerRole = roleRepository.findByName(RoleType.CUSTOMER)
                .orElseThrow(() -> new RuntimeException("CUSTOMER role not found"));
        // Tạo User
        User user = User.builder()
                .email(registerRequest.getEmail())
                .passwordHash(passwordEncoder.encode(registerRequest.getPassword()))
                .fullName(registerRequest.getFullName())
                .phoneNumber(registerRequest.getPhoneNumber())
                .cccdNumber(registerRequest.getCccdNumber())
                .gender(registerRequest.getGender())
                .dateOfBirth(registerRequest.getDateOfBirth())
                .userStatus(UserStatus.ACTIVE)
                .role(customerRole)
                .build();

        // SAVE DATABASE
        userRepository.save(user);

        // RETURN RESPONSE
        return AuthResponse.builder()
                .message("Register successful")
                .build();

    }
}
