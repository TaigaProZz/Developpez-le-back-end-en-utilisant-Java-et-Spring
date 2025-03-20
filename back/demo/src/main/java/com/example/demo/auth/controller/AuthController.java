package com.example.demo.auth.controller;

import com.example.demo.auth.dto.LoginDto;
import com.example.demo.auth.service.AuthService;
import com.example.demo.user.dto.GetUserDto;
import com.example.demo.auth.dto.RegisterDto;
import com.example.demo.auth.service.JwtService;
import com.example.demo.user.model.User;
import com.example.demo.user.repository.UserRepository;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import com.example.demo.user.service.UserService;
import com.fasterxml.jackson.core.TreeCodec;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final UserService userService;
  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final AuthService authService;

  public AuthController(UserService userService, JwtService jwtService, PasswordEncoder passwordEncoder, UserRepository userRepository, AuthService authService, TreeCodec treeCodec) {
    this.userService = userService;
    this.jwtService = jwtService;
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
    this.authService = authService;
  }

  @PostMapping("/login")
  public ResponseEntity<Map<String,Object>> login(@RequestBody LoginDto loginDto) {
    // try to find user by email
    User user = userService.findUserByEmail(loginDto.getEmail());
       
    // if user not found or password is incorrect
    if (user == null || !passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
      Map<String, Object> errorResponse = new HashMap<>();
      errorResponse.put("message", "error");
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    // generate token and return it
    String token = jwtService.generateToken(user.getEmail());
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
    // get current user from context

    User user = userRepository.findByEmail(principal.getName()).orElse(null);

    if (user == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utilisateur introuvable");
    }

    // map to dto
    GetUserDto getUserDto = new GetUserDto();
    getUserDto.setId(user.getId());
    getUserDto.setEmail(user.getEmail());
    getUserDto.setName(user.getName());
    getUserDto.setCreated_at(user.getCreatedAt());
    getUserDto.setUpdated_at(user.getUpdatedAt());

    return ResponseEntity.ok(getUserDto);
  }
}
