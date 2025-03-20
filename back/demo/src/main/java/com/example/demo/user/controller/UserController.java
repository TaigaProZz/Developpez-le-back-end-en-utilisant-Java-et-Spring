package com.example.demo.user.controller;

import com.example.demo.user.dto.GetUserDto;
import com.example.demo.user.model.User;
import com.example.demo.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getUserById(@PathVariable Long id) {
    // try to find user by id
    User user = userService.findUserById(id);

    // map to dto
    GetUserDto getUserDto = new GetUserDto();
    getUserDto.setId(user.getId());
    getUserDto.setEmail(user.getEmail());
    getUserDto.setName(user.getName());
    getUserDto.setCreated_at(user.getCreatedAt());
    getUserDto.setUpdated_at(user.getUpdatedAt());

    // and return it
    return ResponseEntity.ok(getUserDto);
  }
}
