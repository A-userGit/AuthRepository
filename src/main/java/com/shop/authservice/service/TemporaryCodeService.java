package com.shop.authservice.service;

import com.shop.authservice.entity.TemporaryCode;
import com.shop.authservice.entity.UserCredentials;
import com.shop.authservice.enums.TemporaryCodeType;

public interface TemporaryCodeService {
  String createCode(UserCredentials userCredentials, TemporaryCodeType type);

  TemporaryCode getVerifiedCode(String code, TemporaryCodeType type);
}
