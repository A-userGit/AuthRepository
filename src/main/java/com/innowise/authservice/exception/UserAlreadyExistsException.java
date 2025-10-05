package com.innowise.authservice.exception;

public class UserAlreadyExistsException extends RuntimeException {

  public UserAlreadyExistsException(String message) {
    super(message);
  }

  public static UserAlreadyExistsException invalidArgsException(String login) {
    String message = String.format(
        "User with login %s already exists", login);
    return new UserAlreadyExistsException(message);
  }
}
