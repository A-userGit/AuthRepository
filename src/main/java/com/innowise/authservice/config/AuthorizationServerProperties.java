package com.innowise.authservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "spring.security.oauth2.authorizationserver")
public class AuthorizationServerProperties {

  private String issuerUrl;
  private String introspectionEndpoint;
  private int deleteCodeTTL;
  private int accessTokenTTLMinutes;
  private int refreshTokenTTLHours;
  private int refreshCodeLength;
}
