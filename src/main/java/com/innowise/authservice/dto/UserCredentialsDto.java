package com.innowise.authservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserCredentialsDto {

  @NotNull
  private final String login;
  @NotNull
  private final String password;
  private final long userId;
}