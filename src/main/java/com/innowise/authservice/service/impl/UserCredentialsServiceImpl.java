package com.innowise.authservice.service.impl;

import com.innowise.authservice.dto.AuthResponseDto;
import com.innowise.authservice.dto.UserCredentialsDto;
import com.innowise.authservice.entity.UserCredentials;
import com.innowise.authservice.exception.UserAlreadyExistsException;
import com.innowise.authservice.mapper.UserCredentialsMapper;
import com.innowise.authservice.repository.UserCredentialsRepository;
import com.innowise.authservice.service.UserCredentialsService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCredentialsServiceImpl implements UserCredentialsService {

  private final UserCredentialsRepository userCredentialsRepository;
  private final UserCredentialsMapper mapper;

  @Override
  public AuthResponseDto signUp(UserCredentialsDto userCredentialsDto) {
    Optional<UserCredentials> optUserInDB = userCredentialsRepository.findByLogin(
        userCredentialsDto.getLogin());
    if(optUserInDB.isPresent()) {
      throw UserAlreadyExistsException.invalidArgsException(userCredentialsDto.getLogin());
    }
    userCredentialsRepository.save(mapper.toUserCredentials(userCredentialsDto));
    return new AuthResponseDto("","","Bearer");
  }

}
