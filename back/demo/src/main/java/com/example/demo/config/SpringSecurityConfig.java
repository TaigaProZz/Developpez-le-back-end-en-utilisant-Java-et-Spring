package com.example.demo.config;

import com.example.demo.auth.service.CustomUserDetailsService;
import com.example.demo.auth.service.JwtService;
import com.example.demo.auth.filter.JwtAuthenticationFilter;

import com.example.demo.utils.PublicRoutes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration class for enabling and customizing Spring Security within the application.
 * It defines the security configurations, including the authentication manager,
 * security filter chain, and password encoder, to manage and secure access to the APIs.
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

  private final JwtService jwtService;

  public SpringSecurityConfig(JwtService jwtService) {
    this.jwtService = jwtService;
  }

  /**
   * Configures the security filter chain for the application.
   * The configuration includes disabling CSRF protection, managing authentication
   * and authorization rules, setting session management policy to stateless,
   * and allowing access to specific public endpoints.
   *
   * @param http the {@link HttpSecurity} object to customize the security configuration
   * @return a configured {@link SecurityFilterChain} instance
   * @throws Exception if an error occurs during configuration
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                    // allow access to some endpoints without authentication and secure the rest
                    .requestMatchers(
                            PublicRoutes.PUBLIC_URLS
                    ).permitAll()
                    .anyRequest().authenticated())
            // add JWT filter to validate token and set authentication
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(new JwtAuthenticationFilter(jwtService), UsernamePasswordAuthenticationFilter.class)
            .build();
  }

  /**
   * Provides a bean of type {@link PasswordEncoder} that uses the BCrypt hashing function.
   * This encoder is utilized for encoding and verifying passwords in a secure manner.
   *
   * @return an instance of {@link BCryptPasswordEncoder} for password encryption
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * Provides an {@link AuthenticationManager} bean for managing authentication in the application.
   * This method sets up a {@link DaoAuthenticationProvider} with a custom user details service and
   * password encoder for validating user credentials.
   *
   * @param userDetailsService the {@link CustomUserDetailsService} used to retrieve user-specific data
   * @return an {@link AuthenticationManager} instance configured with the specified authentication provider
   */
  @Bean
  public AuthenticationManager authenticationManager(CustomUserDetailsService userDetailsService) {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder());
    return new ProviderManager(authProvider);
  }
}
