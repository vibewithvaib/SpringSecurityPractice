package org.example.springsecuritypractice.config;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
//import org.example.springsecuritypracticeCustomUserDetailsService;
//import org.example.springsecuritypractice.service.CustomUserDetailsService;
import org.example.springsecuritypractice.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.http.HttpStatus;

@Configuration
@EnableWebSecurity // This annotation enables Spring's web security support
@RequiredArgsConstructor
public class SecurityConfig {


   private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        // This bean is required for the authentication process
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Disable CSRF protection for our stateless REST API
                .csrf(AbstractHttpConfigurer::disable)

                // 2. Configure exception handling for authentication errors
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                )

                // 3. Define authorization rules for different endpoints
                .authorizeHttpRequests(auth -> auth
                        // Allow public access to registration and login endpoints
                        .requestMatchers("/api/auth/**").permitAll()
                        // Require ADMIN role for the /api/admin/** endpoints
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        // Require USER or ADMIN role for /api/user/** endpoints
                        .requestMatchers("/api/user/**").hasAnyRole("USER", "ADMIN")
                        // All other requests must be authenticated
                        .anyRequest().authenticated()
                )

                // 4. Configure form-based login
                .formLogin(form -> form
                        // The URL to submit the username and password to
                        .loginProcessingUrl("/api/auth/login")
                        // Use email as the username parameter
                        .usernameParameter("email")
                        // On successful login, return HTTP 200 OK
                        .successHandler((request, response, authentication) -> response.setStatus(HttpServletResponse.SC_OK))
                        // On failed login, return HTTP 401 Unauthorized
                        .failureHandler((request, response, exception) -> response.setStatus(HttpServletResponse.SC_UNAUTHORIZED))
                        .permitAll()
                )

                // 5. Configure logout
                .logout(logout -> logout
                        // The URL to trigger logout
                        .logoutUrl("/api/auth/logout")
                        // On successful logout, return HTTP 200 OK
                        .logoutSuccessHandler((request, response, authentication) -> response.setStatus(HttpServletResponse.SC_OK))
                        .permitAll()
                );

        return http.build();
    }
}