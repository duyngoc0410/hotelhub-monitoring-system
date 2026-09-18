package com.hotelhub.backend.auth.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthResponse {
    private String message;

    private String accessToken;

    private String refreshToken;

    private String tokenType;
}
