package com.hotelhub.backend.security.jwt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;


    @SpringBootTest
    class JwtServiceTest {

        @Autowired
        private JwtService jwtService;

        private UserDetails createTestUser() {

            return User.builder()
                    .username("test@hotelhub.com")
                    .password("123456")
                    .roles("CUSTOMER")

                    .build();
        }

        @Test
        void shouldGenerateAccessToken() {

            UserDetails userDetails = createTestUser();

            String token = jwtService.generateAccessTonken(userDetails);

            assertNotNull(token);
            assertFalse(token.isBlank());

            System.out.println("ACCESS TOKEN:");
            System.out.println(token);
        }

        @Test
        void shouldExtractUsername() {

            UserDetails userDetails = createTestUser();

            String token = jwtService.generateAccessTonken(userDetails);

            String username = jwtService.extractUsername(token);

            assertEquals(
                    "test@hotelhub.com",
                    username
            );

            System.out.println("USERNAME:");
            System.out.println(username);
        }

        @Test
        void shouldExtractExpiration() {

            UserDetails userDetails = createTestUser();

            String token = jwtService.generateAccessTonken(userDetails);

            Date expiration = jwtService.extractExpiration(token);

            assertNotNull(expiration);
            assertTrue(expiration.after(new Date()));

            System.out.println("EXPIRATION:");
            System.out.println(expiration);
        }

        @Test
        void shouldValidateToken() {

            UserDetails userDetails = createTestUser();

            String token = jwtService.generateAccessTonken(userDetails);

            boolean valid = jwtService.isTokenValid(
                    token,
                    userDetails
            );

            assertTrue(valid);

            System.out.println("TOKEN VALID:");
            System.out.println(valid);
        }

        @Test
        void shouldGenerateRefreshToken() {

            UserDetails userDetails = createTestUser();

            String token = jwtService.generateRefreshToken(userDetails);

            assertNotNull(token);
            assertFalse(token.isBlank());

            System.out.println("REFRESH TOKEN:");
            System.out.println(token);
        }
    }

