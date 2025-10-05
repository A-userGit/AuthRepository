package com.innowise.authservice.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class AuthResponseDto {

  private final String authenticationToken;
  private final String refreshToken;
  private final String bearer;
}
