package com.shop.authservice.service;

import com.shop.authservice.dto.AuthResponseDto;
import com.shop.authservice.dto.UserCredentialsDto;

public interface UserCredentialsService {
  AuthResponseDto signUp(UserCredentialsDto userCredentialsDto);

  void delete(long id);

  void delete(String code);
}
