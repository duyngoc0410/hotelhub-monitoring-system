package com.hotelhub.backend.auth.controller;

import com.hotelhub.backend.auth.dto.reponse.AuthResponse;
import com.hotelhub.backend.auth.dto.request.CustomerRegisterRequest;
import com.hotelhub.backend.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody CustomerRegisterRequest customerRegisterRequest) {
        return authService.register(customerRegisterRequest);
    }

}
