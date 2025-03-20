package com.example.demo.auth.controller;

import com.example.demo.auth.dto.LoginDto;
import com.example.demo.auth.service.AuthService;
import com.example.demo.user.dto.GetUserDto;
import com.example.demo.auth.dto.RegisterDto;

import java.security.Principal;
import java.util.HashMap;
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
  public ResponseEntity<Map<String,Object>> login(@RequestBody LoginDto loginDto) {
    // try to log in and get token
    String token = this.authService.login(loginDto);

    Map<String, Object> response = new HashMap<>();
    response.put("token", token);
    return ResponseEntity.ok(response);
  }


  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody RegisterDto registerDto) {
    // save user and get the token
    String token = authService.registerUser(registerDto);

    Map<String, Object> response = new HashMap<>();
    response.put("token", token);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/me")
  public ResponseEntity<?> me(Principal principal) {
    // get user infos
    GetUserDto getUserDto = authService.me(principal);

    return ResponseEntity.ok(getUserDto);
  }
}
