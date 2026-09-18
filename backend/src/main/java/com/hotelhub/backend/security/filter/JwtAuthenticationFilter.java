package com.hotelhub.backend.security.filter;

import com.hotelhub.backend.security.jwt.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.hotelhub.backend.security.custom.CustomUserDetailsService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // 1. Lấy Authorization Header
        final String authHeader =
                request.getHeader("Authorization");

        // 2. Không có Bearer Token
        if (authHeader == null
                || !authHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;
        }

        // 3. Lấy JWT, bỏ "Bearer "
        final String jwt =
                authHeader.substring(7);

        try {

            // 4. Lấy subject từ JWT
            // CUSTOMER  -> email
            // MANAGEMENT -> CCCD
            final String username =
                    jwtService.extractUsername(jwt);

            // 5. Lấy loại đăng nhập
            final String loginType =
                    jwtService.extractLoginType(jwt);

            // 6. Chỉ xác thực nếu SecurityContext chưa có Authentication
            if (username != null
                    && SecurityContextHolder
                    .getContext()
                    .getAuthentication() == null) {

                UserDetails userDetails;

                // 7. CUSTOMER LOGIN
                if ("CUSTOMER".equals(loginType)) {

                    userDetails =
                            customUserDetailsService
                                    .loadUserByUsername(username);

                }

                // 8. MANAGEMENT LOGIN
                else if ("MANAGEMENT".equals(loginType)) {

                    userDetails =
                            customUserDetailsService
                                    .loadUserByCccd(username);

                }

                // 9. Login type không hợp lệ
                else {

                    filterChain.doFilter(
                            request,
                            response
                    );

                    return;
                }

                // 10. Kiểm tra JWT hợp lệ
                if (jwtService.isTokenValid(
                        jwt,
                        userDetails
                )) {

                    // 11. Tạo Authentication
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    // 12. Gắn thông tin request
                    authentication.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request)
                    );

                    // 13. Đưa Authentication vào SecurityContext
                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(
                                    authentication
                            );
                }
            }

        } catch (Exception e) {

            /*
             * JWT lỗi / hết hạn / không hợp lệ.
             *
             * Hiện tại chỉ bỏ qua authentication
             * và cho request đi tiếp.
             *
             * Exception Handler sẽ được hoàn thiện
             * ở Step xử lý Authentication Exception.
             */
        }

        // 14. Tiếp tục Filter Chain
        filterChain.doFilter(request, response);
    }
}
