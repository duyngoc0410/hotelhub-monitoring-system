package com.hotelhub.backend.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test/role")
public class RoleAuthorizationTestController {
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminOnly() {
        return "ADMIN access granted";
    }

    @GetMapping("/owner")
    @PreAuthorize("hasRole('OWNER')")
    public String ownerOnly() {
        return "OWNER access granted";
    }

    @GetMapping("/staff")
    @PreAuthorize("hasRole('STAFF')")
    public String staffOnly() {
        return "STAFF access granted";
    }
    @GetMapping("/customer")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String customerOnly() {
        return "CUSTOMER access granted";
    }
}
