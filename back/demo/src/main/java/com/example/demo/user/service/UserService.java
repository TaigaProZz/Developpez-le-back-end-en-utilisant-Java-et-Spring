package com.example.demo.user.service;

import com.example.demo.user.model.User;
import com.example.demo.user.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;

  public User findUserByEmail(String email) {
    return this.userRepository.findByEmail(email).orElse(null);
  }
}
