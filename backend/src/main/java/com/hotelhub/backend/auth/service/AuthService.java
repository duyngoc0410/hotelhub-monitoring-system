package com.hotelhub.backend.auth.service;

import com.hotelhub.backend.auth.dto.AuthResponse;
import com.hotelhub.backend.auth.dto.RegisterRequest;
import com.hotelhub.backend.user.entity.User;

import com.hotelhub.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse register(RegisterRequest registerRequest){
        if(userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }
        User user = User.builder()
                .email(registerRequest.getEmail())
                .passwordHash(passwordEncoder.encode(registerRequest.getPassword()))
                .fullName(registerRequest.getFullName())
                .phoneNumber(registerRequest.getPhoneNumber())
                .role("CUSTOMER")
                .status("ACTIVE")
                .build();

        // SAVE DATABASE
        userRepository.save(user);

        // RETURN RESPONSE
        return AuthResponse.builder()
                .message("Register successful")
                .build();

    }
}
