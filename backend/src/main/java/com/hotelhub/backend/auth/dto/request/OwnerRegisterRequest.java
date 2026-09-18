package com.hotelhub.backend.auth.dto.request;

import com.hotelhub.backend.common.constant.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class OwnerRegisterRequest {
    @NotBlank(message = "CCCD number is required")
    @Pattern(
            regexp = "^\\d{12}$",
            message = "CCCD must contain exactly 12 digits"
    )
    private String cccdNumber;

    @NotBlank(message = "Password is required")
    private String password;

    @Email(message = "Invalid email")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    private Gender gender;

    private LocalDate dateOfBirth;
}
