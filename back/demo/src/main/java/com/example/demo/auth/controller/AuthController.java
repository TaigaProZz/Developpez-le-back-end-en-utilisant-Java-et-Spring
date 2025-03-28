package com.example.demo.auth.controller;

import com.example.demo.auth.dto.LoginDto;
import com.example.demo.auth.dto.TokenDto;
import com.example.demo.auth.service.AuthService;
import com.example.demo.user.dto.GetUserDto;
import com.example.demo.auth.dto.RegisterDto;

import java.security.Principal;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/login")
  public ResponseEntity<TokenDto> login(@RequestBody LoginDto loginDto) {
    // try to log in and get token
    String token = this.authService.login(loginDto);
    return ResponseEntity.ok(new TokenDto(token));
  }

  @PostMapping("/register")
  public ResponseEntity<TokenDto> register(@RequestBody RegisterDto registerDto) {
    // save user and get the token
    String token = authService.registerUser(registerDto);
    return ResponseEntity.ok(new TokenDto(token));
  }

  // return authenticated user infos
  @GetMapping("/me")
  public ResponseEntity<GetUserDto> me(Principal principal) {
    // get user infos
    GetUserDto getUserDto = authService.me(principal);
    return ResponseEntity.ok(getUserDto);
  }
}
