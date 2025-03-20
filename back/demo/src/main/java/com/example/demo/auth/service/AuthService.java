package com.example.demo.auth.service;

import com.example.demo.auth.dto.RegisterDto;
import com.example.demo.errors.EmailAlreadyUsedException;
import com.example.demo.errors.UserNotFoundException;
import com.example.demo.user.dto.GetUserDto;
import com.example.demo.user.model.User;
import com.example.demo.user.service.UserService;
import org.springframework.stereotype.Service;

import java.security.Principal;


@Service
public class AuthService {
  private final UserService userService;
  private final JwtService jwtService;

  public AuthService(UserService userService, JwtService jwtService) {
    this.userService = userService;
    this.jwtService = jwtService;
  }

  /**
   * Registers a new user by saving their details and generating a JWT token.
   *
   * @param registerDto An object containing the registration details of the user,
   *                    including email, password, and name.
   * @return A JWT token for the successfully registered user.
   */
  public String registerUser(RegisterDto registerDto) {

    if (this.userService.findUserByEmail(registerDto.getEmail()) != null) {
      throw new EmailAlreadyUsedException("Email déjà utilisée.");
    }

    this.userService.saveUser(registerDto);
    return jwtService.generateToken(registerDto.getEmail());
  }


  public GetUserDto me(Principal principal) {
    // get current user from context
    User user = userService.findUserByEmail(principal.getName());

    if (user == null) {
      throw new UserNotFoundException("Utilisateur introuvable.");
    }

    // map to dto
    GetUserDto getUserDto = new GetUserDto();
    getUserDto.setId(user.getId());
    getUserDto.setEmail(user.getEmail());
    getUserDto.setName(user.getName());
    getUserDto.setCreated_at(user.getCreatedAt());
    getUserDto.setUpdated_at(user.getUpdatedAt());
    return getUserDto;
  }

}
