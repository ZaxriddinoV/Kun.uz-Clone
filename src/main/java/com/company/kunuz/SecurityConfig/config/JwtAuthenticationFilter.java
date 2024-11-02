package com.company.kunuz.SecurityConfig.config;

import com.company.kunuz.Profile.dto.JwtDTO;
import com.company.kunuz.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = header.substring(7).trim();

        try {
            // Access tokenni dekod qilish va tekshirish
            JwtDTO dto = JwtUtil.decode(token);

            String username = dto.getUsername();
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Foydalanuvchini autentifikatsiya qilish
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (ExpiredJwtException e) {
            // Agar access token muddati tugagan bo'lsa, refresh token yordamida yangilash
            String refreshToken = request.getHeader("Refresh-Token");

            if (refreshToken != null) {
                try {
                    JwtDTO refreshDto = JwtUtil.decode(refreshToken);

                    // Refresh token orqali yangi access token yaratish
                    if ("refresh".equals(refreshDto.getType())) {
                        String newAccessToken = JwtUtil.encode(refreshDto.getUsername(), refreshDto.getRole());

                        // Javobga yangi access tokenni qo'shish
                        response.setHeader("New-Access-Token", newAccessToken);

                        // Foydalanuvchini autentifikatsiya qilish
                        UserDetails userDetails = userDetailsService.loadUserByUsername(refreshDto.getUsername());
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                } catch (JwtException ex) {
                    // Agar refresh token yaroqsiz bo'lsa, foydalanuvchini autentifikatsiyadan chiqarish
                    SecurityContextHolder.clearContext();
                }
            } else {
                // Refresh token mavjud emas bo'lsa
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
    }

}
