package com.example.demo.auth.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.auth.service.AuthService;
import com.example.demo.user.model.User;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  private AuthService authService;

  // @PostMapping(path = "/login")
  public ResponseEntity<String> login(@RequestBody User loginRequest) {
  // System.out.println(loginRequest);
  String token = authService.login(loginRequest.getEmail(),
  loginRequest.getPassword());
  return ResponseEntity.ok(token);
  }

  // @PostMapping(path = "/login")
  // public ResponseEntity<String> login(@RequestBody User loginRequest) {
  //   // System.out.println(loginRequest);
  //   String token = authService.login(loginRequest.getEmail(), loginRequest.getPassword());
  //   return ResponseEntity.ok(token);
  // }

}
