package com.innowise.authservice.controller;

import com.innowise.authservice.dto.AuthResponseDto;
import com.innowise.authservice.dto.UserCredentialsDto;
import com.innowise.authservice.service.UserCredentialsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authorization")
@RequiredArgsConstructor
public class UserCredentialsController {

  private final UserCredentialsService userCredentialsService;

  @PostMapping("create")
  ResponseEntity<AuthResponseDto> createUser(@Valid @RequestBody UserCredentialsDto credentialsDto) {
    AuthResponseDto authResponseDto = userCredentialsService.signUp(credentialsDto);
    return new ResponseEntity<>(authResponseDto, HttpStatus.CREATED);
  }
}
