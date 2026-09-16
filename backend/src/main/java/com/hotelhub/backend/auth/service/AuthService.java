package com.hotelhub.backend.auth.service;

import com.hotelhub.backend.auth.dto.reponse.AuthResponse;
import com.hotelhub.backend.auth.dto.request.CustomerRegisterRequest;
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
    public AuthResponse register(CustomerRegisterRequest customerRegisterRequest){
        // Kiểm Tra Email
        if (userRepository.existsByEmail(customerRegisterRequest.getEmail())) {
            throw new RuntimeException("Email already in use");
        }
        // Kiểm Tra Phone
        if (userRepository.existsByPhoneNumber(
                customerRegisterRequest.getPhoneNumber())) {

            throw new RuntimeException("Phone number already in use");
        }
        // Kiểm tra cccd
        if (customerRegisterRequest.getCccdNumber() != null
                && !customerRegisterRequest.getCccdNumber().isBlank()
                && userRepository.existsByCccdNumber(
                customerRegisterRequest.getCccdNumber())) {

            throw new RuntimeException("CCCD already in use");
        }
        // 2. Lấy Role CUSTOMER
        Role customerRole = roleRepository.findByName(RoleType.CUSTOMER)
                .orElseThrow(() -> new RuntimeException("CUSTOMER role not found"));
        // Tạo User
        User user = User.builder()
                .email(customerRegisterRequest.getEmail())
                .passwordHash(passwordEncoder.encode(customerRegisterRequest.getPassword()))
                .fullName(customerRegisterRequest.getFullName())
                .phoneNumber(customerRegisterRequest.getPhoneNumber())
                .cccdNumber(customerRegisterRequest.getCccdNumber())
                .gender(customerRegisterRequest.getGender())
                .dateOfBirth(customerRegisterRequest.getDateOfBirth())
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
