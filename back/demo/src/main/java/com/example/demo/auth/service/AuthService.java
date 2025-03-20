package com.example.demo.auth.service;

import com.example.demo.auth.dto.LoginDto;
import com.example.demo.auth.dto.RegisterDto;
import com.example.demo.errors.EmailAlreadyUsedException;
import com.example.demo.errors.LoginFailedException;
import com.example.demo.errors.UserNotFoundException;
import com.example.demo.user.dto.GetUserDto;
import com.example.demo.user.model.User;
import com.example.demo.user.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;


@Service
public class AuthService {
  private final UserService userService;
  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;

  public AuthService(UserService userService, JwtService jwtService, PasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.jwtService = jwtService;
    this.passwordEncoder = passwordEncoder;
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

  /**
   * Logs a user into the system by validating their email and password credentials.
   * If authentication is successful, a JWT token is generated and returned.
   *
   * @param loginDto An object containing the user's email and password used for authentication.
   * @return A JWT token for the authenticated user.
   * @throws LoginFailedException If the email does not correspond to a registered user or
   *                              if the provided password is incorrect.
   */
  public String login(LoginDto loginDto) {
    // try to find user by email
    User user = userService.findUserByEmail(loginDto.getEmail());

    // if user not found or password is incorrect
    if (user == null || !passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
      throw new LoginFailedException("Email ou mot de passe incorrect.");
    }

    // generate token and return it
    return jwtService.generateToken(user.getEmail());
  }


  /**
   * Retrieves the currently logged-in user's details based on the provided {@link Principal}.
   * The method fetches the user from the database using their email, which is derived from the principal.
   * If the user is found, their details are mapped into a {@link GetUserDto}.
   *
   * @param principal The security principal representing the currently authenticated user,
   *                  containing their authentication details such as email.
   * @return A {@link GetUserDto} object containing the details of the currently logged-in user,
   *         including their ID, email, name, creation timestamp, and last updated timestamp.
   * @throws UserNotFoundException If no user is found in the database with the email obtained from the principal.
   */
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
