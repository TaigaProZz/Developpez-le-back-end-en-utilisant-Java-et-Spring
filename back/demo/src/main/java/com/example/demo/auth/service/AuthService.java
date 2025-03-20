package com.example.demo.auth.service;

import com.example.demo.auth.dto.RegisterDto;
import com.example.demo.errors.EmailAlreadyUsedException;
import com.example.demo.user.service.UserService;
import org.springframework.stereotype.Service;


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
    // check if email is already used
    if (this.userService.findUserByEmail(registerDto.getEmail()) != null) {
      throw new EmailAlreadyUsedException("Email déjà utilisée.");
    }

    this.userService.saveUser(registerDto);
    return jwtService.generateToken(registerDto.getEmail());
  }

}
