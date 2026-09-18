package com.hotelhub.backend.auth.service;

import com.hotelhub.backend.auth.dto.response.AuthResponse;
import com.hotelhub.backend.auth.dto.request.CustomerLoginRequest;
import com.hotelhub.backend.auth.dto.request.CustomerRegisterRequest;
import com.hotelhub.backend.auth.dto.request.ManagementLoginRequest;
import com.hotelhub.backend.auth.dto.request.OwnerRegisterRequest;
import com.hotelhub.backend.common.constant.enums.RoleType;
import com.hotelhub.backend.common.constant.enums.UserStatus;
import com.hotelhub.backend.role.entity.Role;
import com.hotelhub.backend.role.repository.RoleRepository;
import com.hotelhub.backend.security.jwt.JwtService;
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
    private final JwtService jwtService;
    @Transactional
    public AuthResponse customerRegister(CustomerRegisterRequest request) {

        // 1. Check email
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        // 2. Check phone
        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new RuntimeException("Phone number already in use");
        }

        // 3. Get CUSTOMER role
        Role customerRole = roleRepository.findByName(RoleType.CUSTOMER)
                .orElseThrow(() ->
                        new RuntimeException("CUSTOMER role not found"));

        // 4. Create User
        User user = User.builder()
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .phoneNumber(request.getPhoneNumber())
                .gender(request.getGender())
                .dateOfBirth(request.getDateOfBirth())
                .userStatus(UserStatus.ACTIVE)
                .role(customerRole)
                .build();

        // 5. Save
        userRepository.save(user);

        // 6. Response
        return AuthResponse.builder()
                .message("Customer registered successfully")
                .build();


    }
    @Transactional
    public AuthResponse ownerRegister(OwnerRegisterRequest request) {

        // 1. Check CCCD
        if (userRepository.existsByCccdNumber(request.getCccdNumber())) {
            throw new RuntimeException("CCCD already in use");
        }

        // 2. Check email
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        // 3. Check phone
        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new RuntimeException("Phone number already in use");
        }

        // 4. Get OWNER role
        Role ownerRole = roleRepository.findByName(RoleType.OWNER)
                .orElseThrow(() ->
                        new RuntimeException("OWNER role not found"));

        // 5. Create User
        User user = User.builder()
                .cccdNumber(request.getCccdNumber())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .phoneNumber(request.getPhoneNumber())
                .gender(request.getGender())
                .dateOfBirth(request.getDateOfBirth())
                .userStatus(UserStatus.ACTIVE)
                .role(ownerRole)
                .build();

        // 6. Save
        userRepository.save(user);

        // 7. Response
        return AuthResponse.builder()
                .message("Owner registered successfully")
                .build();
    }
    @Transactional
    public AuthResponse customerLogin(CustomerLoginRequest request){
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));
        if (user.getRole().getName() != RoleType.CUSTOMER){
            throw new RuntimeException("Invalid customer account");
        }
        if (user.getUserStatus() != UserStatus.ACTIVE){
            throw new RuntimeException("Account is not active");
        }
        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPasswordHash()
        )){
            throw new RuntimeException("Invalid email or password");
        }

        String accessToken = jwtService.generateAccessToken(user, "CUSTOMER");
        String refreshToken = jwtService.generateRefreshToken(
                user,
                "CUSTOMER"
        );

        return AuthResponse.builder()
                .message("Customer login successfully")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .build();
    }

    @Transactional
    public AuthResponse managementLogin(
            ManagementLoginRequest request
    ){
        User user = userRepository.findByCccdNumber(request.getCccdNumber()).orElseThrow(() -> new RuntimeException("Invalid CCCD or password"));
        RoleType roleType = user.getRole().getName();
        if (roleType != RoleType.ADMIN
        && roleType != RoleType.OWNER
        && roleType != RoleType.STAFF){
            throw new RuntimeException("Invalid management account");
        }

        if (user.getUserStatus() != UserStatus.ACTIVE){
            throw new RuntimeException("Account is not active");
        }
        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPasswordHash()
        )){
            throw new RuntimeException("Invalid CCCD or password");
        }
        String accessToken =
                jwtService.generateAccessToken(
                        user,
                        "MANAGEMENT"
                );

        String refreshToken =
                jwtService.generateRefreshToken(
                        user,
                        "MANAGEMENT"
                );

        return AuthResponse.builder()
                .message("Management login successfully")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .build();
    }

}
