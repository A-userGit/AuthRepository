package com.shop.authservice.service.model;

import com.shop.authservice.entity.UserCredentials;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

  private static final String DEFAULT_ROLE = "DEFAULT";
  private final UserCredentials userCredentials;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(DEFAULT_ROLE));
  }

  @Override
  public String getPassword() {
    return new String(userCredentials.getHashedPassword(), StandardCharsets.UTF_8);
  }

  @Override
  public String getUsername() {
    return userCredentials.getLogin();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
