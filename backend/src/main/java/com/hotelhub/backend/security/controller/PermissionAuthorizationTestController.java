package com.hotelhub.backend.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test/permission")
public class PermissionAuthorizationTestController {
    @GetMapping("/hotel-create")
    @PreAuthorize("hasAuthority('HOTEL_CREATE')")
    public String hotelCreate() {
        return "HOTEL_CREATE permission granted";
    }
}
