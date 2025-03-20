package com.example.demo.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
 /**
  * Constructs a new UserNotFoundException with the specified detail message.
  *
  * @param message the detail message that provides information about the user
  *                not being found.
  */
   public UserNotFoundException(String message) {
      super(message);
    }
}
