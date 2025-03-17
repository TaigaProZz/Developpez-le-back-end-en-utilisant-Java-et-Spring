package com.example.demo.auth.filter;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.auth.service.JwtService;

import java.io.IOException;

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

    // extract token from request
    String token = extractJwtFromRequest(request);

    if (token == null || !jwtService.validateToken(token)) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().flush();
      return;
    }

    String email = jwtService.getEmailFromToken(token);
    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, null, null);
    SecurityContextHolder.getContext().setAuthentication(authentication);

    filterChain.doFilter(request, response);
  }

  private String extractJwtFromRequest(jakarta.servlet.http.HttpServletRequest request) {
    // get token from authorization header
    String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }
}
