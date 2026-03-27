package com.shop.authservice.service.impl;

import com.shop.authservice.dto.AuthResponseDto;
import com.shop.authservice.dto.UserCredentialsDto;
import com.shop.authservice.entity.TemporaryCode;
import com.shop.authservice.entity.UserCredentials;
import com.shop.authservice.enums.TemporaryCodeType;
import com.shop.authservice.exception.ObjectNotFoundException;
import com.shop.authservice.exception.UserAlreadyExistsException;
import com.shop.authservice.mapper.UserCredentialsMapper;
import com.shop.authservice.repository.UserCredentialsRepository;
import com.shop.authservice.service.TemporaryCodeService;
import com.shop.authservice.service.UserCredentialsService;
import com.shop.authservice.util.EncryptionUtils;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCredentialsServiceImpl implements UserCredentialsService {

  private final UserCredentialsRepository userCredentialsRepository;
  private final TemporaryCodeService temporaryCodeService;
  private final UserCredentialsMapper mapper;

  @Override
  @Transactional
  public AuthResponseDto signUp(UserCredentialsDto userCredentialsDto) {
    Optional<UserCredentials> optUserInDB = userCredentialsRepository.findByLogin(
        userCredentialsDto.getLogin());
    if (optUserInDB.isPresent()) {
      throw UserAlreadyExistsException.invalidArgsException(userCredentialsDto.getLogin());
    }
    UserCredentials userCredentials = mapper.toUserCredentials(userCredentialsDto);
    userCredentials.setHashedPassword(EncryptionUtils.getHash(userCredentialsDto.getPassword()));
    UserCredentials savedCredentials = userCredentialsRepository.save(userCredentials);
    String code = temporaryCodeService.createCode(savedCredentials, TemporaryCodeType.CRED_DELETE);
    return new AuthResponseDto(savedCredentials.getId(), code);
  }

  @Override
  @Lock(LockModeType.PESSIMISTIC_WRITE)
  public void delete(long id) {
      checkIfExists(id);
      userCredentialsRepository.deleteById(id);
  }

  @Override
  public void delete(String code) {
    TemporaryCode verifiedCode = temporaryCodeService.getVerifiedCode(code,
        TemporaryCodeType.CRED_DELETE);
    if (verifiedCode == null) {
      throw new  AccessDeniedException("Code expired");
    }
    delete(verifiedCode.getUserCredentials().getId());
  }

  private void checkIfExists(long id) {
    boolean exists = userCredentialsRepository.existsById(id);
    if (!exists) {
      throw ObjectNotFoundException.entityNotFound(UserCredentials.class.toString(), "id", id);
    }
  }

}
