package com.shop.authservice.dto;

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
}