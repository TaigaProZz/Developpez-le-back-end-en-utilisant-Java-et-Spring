package com.example.demo.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RentNotFoundException extends RuntimeException {
  /**
   * Constructs a new RentNotFoundException with the specified detail message.
   *
   * @param message the detail message that provides information about the Rent
   *                not being found.
   */
  public RentNotFoundException(String message) {
    super(message);
  }
}
