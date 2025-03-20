package com.example.demo.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class LoginFailedException extends RuntimeException {
  /**
   * Constructs a new LoginFailedException with the specified detail message.
   *
   * @param message the detail message that provides information about the login failure.
   */
  public LoginFailedException(String message) {
    super(message);
  }
}
