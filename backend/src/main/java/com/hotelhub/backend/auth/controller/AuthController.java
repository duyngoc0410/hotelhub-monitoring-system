package com.hotelhub.backend.auth.controller;

import com.hotelhub.backend.auth.dto.response.AuthResponse;
import com.hotelhub.backend.auth.dto.request.CustomerLoginRequest;
import com.hotelhub.backend.auth.dto.request.CustomerRegisterRequest;
import com.hotelhub.backend.auth.dto.request.ManagementLoginRequest;
import com.hotelhub.backend.auth.dto.request.OwnerRegisterRequest;
import com.hotelhub.backend.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/customer/register")
    public AuthResponse customerRegister(@Valid @RequestBody CustomerRegisterRequest customerRegisterRequest) {
        return authService.customerRegister(customerRegisterRequest);
    }

    @PostMapping("/owner/register")
    public AuthResponse ownerRegister(
            @Valid @RequestBody OwnerRegisterRequest ownerRegisterRequest){
        return authService.ownerRegister(ownerRegisterRequest);
    }
    @PostMapping("/customer/login")
    public AuthResponse customerLogin(
            @Valid @RequestBody CustomerLoginRequest request
    ) {
        return authService.customerLogin(request);
    }
    @PostMapping("/management/login")
    public AuthResponse managementLogin(
            @Valid @RequestBody ManagementLoginRequest request
    ) {
        return authService.managementLogin(request);
    }
}
