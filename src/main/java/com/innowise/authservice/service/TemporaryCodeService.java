package com.innowise.authservice.service;

import com.innowise.authservice.entity.TemporaryCode;
import com.innowise.authservice.entity.UserCredentials;
import com.innowise.authservice.enums.TemporaryCodeType;

public interface TemporaryCodeService {
  String createCode(UserCredentials userCredentials, TemporaryCodeType type);

  TemporaryCode getVerifiedCode(String code, TemporaryCodeType type);
}
