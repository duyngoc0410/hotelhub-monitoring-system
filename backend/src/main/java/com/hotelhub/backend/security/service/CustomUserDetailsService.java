package com.hotelhub.backend.security.service;

import com.hotelhub.backend.security.custom.CustomUserDetails;
import com.hotelhub.backend.user.entity.User;
import com.hotelhub.backend.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found with email: " + email
                        )
                );

        return new CustomUserDetails(user);
    }

/*
CustomUserDetailsService la nguoi di lay User tu Database

PostGreSQL -> UserRepository - User > CustomUserDetailsService -> CustomUserDetails -> : Email, PassWord Authority --> ( Role and Permission ) --> Spring Security


 */

}
