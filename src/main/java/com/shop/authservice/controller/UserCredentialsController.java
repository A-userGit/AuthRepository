package com.shop.authservice.controller;

import com.shop.authservice.dto.AuthResponseDto;
import com.shop.authservice.dto.UserCredentialsDto;
import com.shop.authservice.service.UserCredentialsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/authorization")
@RequiredArgsConstructor
public class UserCredentialsController {

  private final UserCredentialsService userCredentialsService;

  @PostMapping("create")
  ResponseEntity<AuthResponseDto> createUser(@Valid @RequestBody UserCredentialsDto credentialsDto) {
    AuthResponseDto authResponseDto = userCredentialsService.signUp(credentialsDto);
    return new ResponseEntity<>(authResponseDto, HttpStatus.CREATED);
  }

  @DeleteMapping("delete")
  ResponseEntity<?> deleteUser(@Param("code") String code) {
    userCredentialsService.delete(code);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
