package com.shop.authservice.repository.impl;

import com.shop.authservice.config.AuthorizationServerProperties;
import com.shop.authservice.entity.ClientAuth;
import com.shop.authservice.mapper.ClientAuthMapper;
import com.shop.authservice.repository.ClientAuthRepository;
import com.shop.authservice.repository.CustomRegisteredClientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

@RequiredArgsConstructor
public class CustomRegisteredClientRepositoryImpl implements CustomRegisteredClientRepository {

  private static final String ALREADY_EXISTS = "Client with id %s already exists";
  private static final String NOT_FOUND = "Client with id %s don't exist";
  private final ClientAuthRepository clientAuthRepository;
  private final ClientAuthMapper mapper;
  private final AuthorizationServerProperties properties;

  @Override
  @Transactional
  public void save(RegisteredClient registeredClient) {
    ClientAuth client = mapper.toClientAuth(registeredClient);
    Optional<ClientAuth> oldClient = clientAuthRepository.findByClientId(
        registeredClient.getClientId());
    if (oldClient.isPresent()) {
      throw new IllegalArgumentException(
          String.format(ALREADY_EXISTS, registeredClient.getClientId()));
    }
    clientAuthRepository.save(client);
  }

  @Override
  public RegisteredClient findById(String id) {
    return null;
  }

  @Override
  public RegisteredClient findByClientId(String clientId) {
    Optional<ClientAuth> byClientId = clientAuthRepository.findByClientId(clientId);
    if (byClientId.isEmpty()) {
      return null;
    }
    ClientAuth client = byClientId.get();
    RegisteredClient registeredClient;
    if(byClientId.get().isPublic()) {
      registeredClient = RegisteredClient.withId(client.getId().toString())
              .clientId(client.getClientId())
              .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
              .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
              .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
              .redirectUris(s -> s.addAll(Arrays.stream(client.getRedirectUri().split(" ")).toList()))
              .scopes(s -> s.addAll(Arrays.stream(client.getScope().split(" ")).toList()))
              .tokenSettings(TokenSettings.builder()
                      .accessTokenTimeToLive(Duration.ofMinutes(properties.getAccessTokenTTLMinutes()))
                      .refreshTokenTimeToLive(Duration.ofHours(properties.getRefreshTokenTTLHours()))
                      .build())
              .clientSettings(ClientSettings.builder()
                      .requireAuthorizationConsent(true)
                      .requireProofKey(true).build())
              .postLogoutRedirectUri(client.getPostLogoutRedirectUri())
              .build();
    }else {
      registeredClient = RegisteredClient.withId(client.getId().toString())
              .clientId(client.getClientId())
              .clientSecret(client.getClientSecret())
              .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
              .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
              .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
              .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
              .redirectUris(s -> s.addAll(Arrays.stream(client.getRedirectUri().split(" ")).toList()))
              .scopes(s -> s.addAll(Arrays.stream(client.getScope().split(" ")).toList()))
              .tokenSettings(TokenSettings.builder()
                      .accessTokenTimeToLive(Duration.ofMinutes(properties.getAccessTokenTTLMinutes()))
                      .refreshTokenTimeToLive(Duration.ofHours(properties.getRefreshTokenTTLHours()))
                      .build())
              .postLogoutRedirectUri(client.getPostLogoutRedirectUri())
              .build();
    }
    return registeredClient;
  }

  @Override
  @Transactional
  public void evictClient(String clientId) {
    Optional<ClientAuth> clientAuthOptional = clientAuthRepository.findByClientId(clientId);
    if (clientAuthOptional.isEmpty()) {
      throw new EntityNotFoundException(String.format(NOT_FOUND, clientId));
    }
    clientAuthRepository.deleteByClientId(clientId);
  }
}
