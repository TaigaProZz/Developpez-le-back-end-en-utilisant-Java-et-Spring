package com.example.demo.user.service;

import com.example.demo.auth.dto.RegisterDto;
import com.example.demo.errors.UserNotFoundException;
import com.example.demo.user.model.User;
import com.example.demo.user.repository.UserRepository;
import lombok.Data;
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

  /**
   * Finds a user by their email address.
   *
   * @param email The email address of the user to be searched.
   * @return The User object if a user with the given email exists; otherwise, null.
   */
  public User findUserByEmail(String email) {
    return this.userRepository.findByEmail(email).orElse(null);
  }

  /**
   * Retrieves a user by their unique identifier.
   *
   * @param id The unique identifier of the user to find.
   * @return The User object matching the given identifier.
   * @throws UserNotFoundException if no user with the specified id is found.
   */
  public User findUserById(Long id) {
    User user = this.userRepository.findById(id).orElse(null);

    if (user == null) {
      throw new UserNotFoundException("Utilisateur introuvable.");
    }
    return user;
  }

  /**
   * Saves a new user based on the registration details provided.
   * The password is encoded before storing in the database.
   *
   * @param registerDto An object containing the registration details of the user,
   *                    including email, password, and name.
   * @return The saved User object.
   */
  public User saveUser(RegisterDto registerDto) {
    User user = new User();
    user.setEmail(registerDto.getEmail());
    user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
    user.setName(registerDto.getName());

    return this.userRepository.save(user);
  }
}
