package com.example.demo.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EmailAlreadyUsedException extends RuntimeException {
  /**
   * Constructs a new EmailAlreadyUsedException with the specified detail message.
   *
   * @param message the detail message, which provides information about the specific
   *                conflict that occurred (e.g., the email address that is already in use).
   */
  public EmailAlreadyUsedException(String message) {
    super(message);
  }
}
