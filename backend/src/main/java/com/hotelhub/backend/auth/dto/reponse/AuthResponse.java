package com.hotelhub.backend.auth.dto.reponse;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthResponse {
    private String message;
}
