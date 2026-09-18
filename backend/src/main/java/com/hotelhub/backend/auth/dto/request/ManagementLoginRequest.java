package com.hotelhub.backend.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagementLoginRequest {
    @NotBlank(message = "CCCD number is required")
    private String cccdNumber;

    @NotBlank(message = "Password is required")
    private String password;
}
