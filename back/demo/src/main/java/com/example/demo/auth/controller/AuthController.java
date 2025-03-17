package com.example.demo.auth.controller;

import com.example.demo.auth.dto.MeDto;
import com.example.demo.auth.dto.RegisterDto;
import com.example.demo.auth.service.JwtService;
import com.example.demo.user.model.User;
import com.example.demo.user.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class AuthController {

  private final UserRepository userRepository;
  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;

  public AuthController(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.jwtService = jwtService;
    this.passwordEncoder = passwordEncoder;
  }

  @PostMapping("/login")
  public ResponseEntity<Map<String,Object>> login(@RequestBody User loginRequest) {
    System.out.println(loginRequest);
    // try to find user by email
    User user = userRepository.findByEmail(loginRequest.getEmail())
        .orElse(null);
       
    // if user not found or password is incorrect
    if (user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
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
    // check if email is already used
    Optional<User> existingUser = userRepository.findByEmail(registerDto.getEmail());

    if (existingUser.isPresent()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Email déjà utilisé");
    }

    // bind user from dto
    User user = new User();
    user.setEmail(registerDto.getEmail());
    user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
    user.setName(registerDto.getName());

    userRepository.save(user);

    // generate token and return it
    String token = jwtService.generateToken(user.getEmail());

    Map<String, Object> response = new HashMap<>();
    response.put("token", token);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/me")
  public ResponseEntity<?> me() {
    // get current user from context
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = (String) authentication.getPrincipal();
    User user = userRepository.findByEmail(email).orElse(null);

    if (user == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utilisateur introuvable");
    }

    // map to dto
    MeDto meDto = new MeDto();
    meDto.setEmail(email);
    meDto.setName(user.getName());
    meDto.setCreated_at(user.getCreatedAt());
    meDto.setUpdated_at(user.getUpdatedAt());

    return ResponseEntity.ok(meDto);
  }
}
