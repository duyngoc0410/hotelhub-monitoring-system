package com.hotelhub.backend.security.custom;


import com.hotelhub.backend.common.constant.enums.UserStatus;
import com.hotelhub.backend.user.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter

public class CustomUserDetails implements UserDetails {
    private final User user;
    private final String username;
    private final Set<GrantedAuthority> authorities;

    public CustomUserDetails(
            User user,
            String username
    ) {
        this.user = user;
        this.username = username;
        this.authorities = new HashSet<>();

        // Role
        if (user.getRole() != null) {
            authorities.add(
                    new SimpleGrantedAuthority(
                            "ROLE_" + user.getRole().getName().name()
                    )
            );

            // Permission
            if (user.getRole().getPermissions() != null) {
                user.getRole()
                        .getPermissions()
                        .forEach(permission ->
                                authorities.add(
                                        new SimpleGrantedAuthority(
                                                permission.getName().name()
                                        )
                                )
                        );
            }
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getUserStatus() == UserStatus.ACTIVE;
    }
}

// CustomUserDetail la 1 Object cua Entity user la cau noi giua User va Security
/*
User -> CustomUserDetails-> Security
 */
