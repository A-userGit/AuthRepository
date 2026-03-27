package com.shop.authservice.service.impl;

import com.shop.authservice.entity.UserCredentials;
import com.shop.authservice.repository.UserCredentialsRepository;
import com.shop.authservice.service.model.CustomUserDetails;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserCredentialsRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<UserCredentials> byLogin = userRepository.findByLogin(username);
    if(byLogin.isEmpty()) {
      throw new UsernameNotFoundException("User not found: " + username);
    }
    return new CustomUserDetails(byLogin.get());
  }
}
