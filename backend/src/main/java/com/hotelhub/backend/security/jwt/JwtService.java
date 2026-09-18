package com.hotelhub.backend.security.jwt;

import com.hotelhub.backend.user.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
// JwtService như 1 spring bean
//
public class JwtService {


    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-token-expiration}")
    private Long accessTokenExpiration;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );
    }
    public String generateAccessToken(
            User user,
            String loginType
    ) {

        Map<String, Object> claims =
                new HashMap<>();

        claims.put("login_type", loginType);

        claims.put(
                "role",
                user.getRole()
                        .getName()
                        .name()
        );

        String subject;

        if ("CUSTOMER".equals(loginType)) {
            subject = user.getEmail();
        } else if ("MANAGEMENT".equals(loginType)) {
            subject = user.getCccdNumber();
        } else {
            throw new IllegalArgumentException("Invalid login type");
        }

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(
                                System.currentTimeMillis()
                                        + accessTokenExpiration
                        )
                )
                .signWith(
                        getSigningKey(),
                        SignatureAlgorithm.HS256
                )
                .compact();
    }

    public String generateRefreshToken(
            User user,
            String loginType
    ) {

        Map<String, Object> claims =
                new HashMap<>();

        claims.put("login_type", loginType);

        String subject;

        if ("CUSTOMER".equals(loginType)) {
            subject = user.getEmail();
        } else if ("MANAGEMENT".equals(loginType)) {
            subject = user.getCccdNumber();
        } else {
            throw new IllegalArgumentException("Invalid login type");
        }

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(
                                System.currentTimeMillis()
                                        + refreshTokenExpiration
                        )
                )
                .signWith(
                        getSigningKey(),
                        SignatureAlgorithm.HS256
                )
                .compact();
    }

    public String extractUsername(String token){
        return extractClaim(
            token,
                Claims::getSubject
        );
    }
    public String extractLoginType(String token) {
        return extractClaim(
                token,
                claims -> claims.get("login_type", String.class)
        );
    }
    public Date extractExpiration(String token) {

        return extractClaim(
                token,
                Claims::getExpiration
        );
    }

    public <T> T extractClaim(
            String token,
            Function<Claims, T> claimsResolver
    ) {

        final Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenExpired(String token) {

        return extractExpiration(token)
                .before(new Date());
    }

    public boolean isTokenValid(
            String token,
            UserDetails userDetails
    ) {

        final String username = extractUsername(token);

        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }


}
