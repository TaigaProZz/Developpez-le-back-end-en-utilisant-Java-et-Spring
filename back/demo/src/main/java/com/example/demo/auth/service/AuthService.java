package com.example.demo.auth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.user.model.User;
import com.example.demo.user.repository.UserRepository;

import lombok.Data;

@Data
@Service
public class AuthService {
  @Autowired
  private UserRepository userRepository;


  public Optional<User> login(String email, String password) {
    Optional<User> user = this.userRepository.findByEmail(email);
    return user;
  }

}
