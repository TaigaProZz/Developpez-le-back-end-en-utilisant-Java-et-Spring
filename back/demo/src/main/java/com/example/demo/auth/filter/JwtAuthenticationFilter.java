package com.example.demo.auth.filter;

import com.example.demo.utils.PublicRoutes;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.auth.service.JwtService;

import java.io.IOException;
import java.util.Arrays;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;

  public JwtAuthenticationFilter(JwtService jwtService) {
    this.jwtService = jwtService;
  }

  @Override
  protected void doFilterInternal(
          jakarta.servlet.http.HttpServletRequest request,
          jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain)
          throws jakarta.servlet.ServletException, IOException {

    String requestPath = request.getServletPath();

    // swagger authorization
    if (requestPath.startsWith("/swagger-ui") || requestPath.startsWith("/api/v3/api-docs")) {
      filterChain.doFilter(request, response);
      return;
    }

    // public endpoints authorization
    if (Arrays.asList(PublicRoutes.PUBLIC_URLS).contains(requestPath)) {
      filterChain.doFilter(request, response);
      return;
    }

    // extract token and check its validity
    String token = extractJwtFromRequest(request);

    // if not valid, return unauthorized err
    if (token == null || !jwtService.validateToken(token)) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().flush();
      return;
    }

    // if valid, authorize the request
    String email = jwtService.getEmailFromToken(token);
    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, null, null);
    SecurityContextHolder.getContext().setAuthentication(authentication);

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
