package com.innowise.authservice.service;

import com.innowise.authservice.dto.AuthResponseDto;
import com.innowise.authservice.dto.UserCredentialsDto;

public interface UserCredentialsService {
  AuthResponseDto signUp(UserCredentialsDto userCredentialsDto);
}
