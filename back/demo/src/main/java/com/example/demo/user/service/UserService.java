package com.example.demo.user.service;

import com.example.demo.auth.dto.RegisterDto;
import com.example.demo.user.model.User;
import com.example.demo.user.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Data
@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public User findUserByEmail(String email) {
    return this.userRepository.findByEmail(email).orElse(null);
  }

  public User saveUser(RegisterDto registerDto) {
    User user = new User();
    user.setEmail(registerDto.getEmail());
    user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
    user.setName(registerDto.getName());

    return this.userRepository.save(user);
  }
}
