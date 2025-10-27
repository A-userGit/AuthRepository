package com.innowise.authservice.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class AuthResponseDto {

  private final long userId;
  private final String tempAbortCode;
}
