package com.example.demo.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Handles the EmailAlreadyUsedException and returns a response entity with an appropriate status
   * code and message.
   *
   * @param e the exception instance containing the details of the email conflict
   * @return a {@link ResponseEntity} with HTTP status 409 (CONFLICT) and the exception message in the response body
   */
  @ExceptionHandler(EmailAlreadyUsedException.class)
  public ResponseEntity<?> handleEmailAlreadyUsedException(EmailAlreadyUsedException e) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
  }
}
