package com.example.demo.auth.filter;


import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.auth.service.JwtService;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;

  private final UserDetailsService userDetailsService;

  public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
    this.jwtService = jwtService;
    this.userDetailsService = userDetailsService;
  }

  /**
   * Processes the incoming HTTP request to authenticate the user based on the JWT token present in the
   * Authorization header. If the token is valid, sets the authentication context with the user's details.
   *
   * @param request      the HTTP request containing the JWT token in the Authorization header
   * @param response     the HTTP response
   * @param filterChain  the filter chain for further request processing
   * @throws jakarta.servlet.ServletException if an error occurs during filter execution
   * @throws IOException                      if an I/O error occurs during filter execution
   */
  @Override
  protected void doFilterInternal(
          jakarta.servlet.http.HttpServletRequest request,
          jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain)
          throws jakarta.servlet.ServletException, IOException {

    try {
      String jwt = extractJwtFromRequest(request);
      if (jwt != null && jwtService.validateToken(jwt)) {
        String username = jwtService.getEmailFromToken(jwt);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (Exception ignored) {
    }

    filterChain.doFilter(request, response);
  }


  /**
   * Extracts the JWT token from the HTTP request's Authorization header.
   *
   * @param request the HTTP request containing the Authorization header from which the JWT token is extracted
   * @return the JWT token if present and valid, otherwise null
   */
  private String extractJwtFromRequest(jakarta.servlet.http.HttpServletRequest request) {
    // get token from authorization header
    String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }
}
