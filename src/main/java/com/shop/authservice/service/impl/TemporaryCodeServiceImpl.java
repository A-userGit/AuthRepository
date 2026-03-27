package com.shop.authservice.service.impl;

import com.shop.authservice.config.AuthorizationServerProperties;
import com.shop.authservice.entity.TemporaryCode;
import com.shop.authservice.entity.UserCredentials;
import com.shop.authservice.enums.TemporaryCodeType;
import com.shop.authservice.repository.TemporaryCodeRepository;
import com.shop.authservice.service.TemporaryCodeService;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TemporaryCodeServiceImpl implements TemporaryCodeService {

  private final TemporaryCodeRepository temporaryCodeRepository;
  private final AuthorizationServerProperties authorizationServerProperties;

  @Override
  public String createCode(UserCredentials userCredentials, TemporaryCodeType type) {
    UUID uuid = UUID.randomUUID();
    TemporaryCode temporaryCode = new TemporaryCode();
    temporaryCode.setType(type);
    temporaryCode.setCode(uuid.toString());
    temporaryCode.setIssuedAt(LocalDateTime.now());
    temporaryCode.setUserCredentials(userCredentials);
    temporaryCodeRepository.save(temporaryCode);
    return uuid.toString();
  }

  @Override
  @Transactional
  public TemporaryCode getVerifiedCode(String code, TemporaryCodeType type) {
    LocalDateTime time = LocalDateTime.now()
        .minusMinutes(authorizationServerProperties.getDeleteCodeTTL());
    return temporaryCodeRepository.getCodeForUser(code, type, time);
  }
}
