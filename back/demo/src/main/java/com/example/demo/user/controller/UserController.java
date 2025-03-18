package com.example.demo.user.controller;

import com.example.demo.user.dto.GetUserDto;
import com.example.demo.user.model.User;
import com.example.demo.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

  private final UserRepository userRepository;

  public UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getUserById(@PathVariable Long id) {

    User user = userRepository.findById(id).orElse(null);

    if (user == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur introuvable");
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
