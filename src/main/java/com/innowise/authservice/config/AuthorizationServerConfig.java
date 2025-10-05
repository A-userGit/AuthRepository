package com.innowise.authservice.config;

import com.innowise.authservice.mapper.ClientAuthMapper;
import com.innowise.authservice.repository.ClientAuthRepository;
import com.innowise.authservice.repository.UserCredentialsRepository;
import com.innowise.authservice.repository.impl.CustomRegisteredClientRepositoryImpl;
import com.innowise.authservice.service.impl.CustomUserDetailsService;
import com.innowise.authservice.util.JwkUtils;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class AuthorizationServerConfig {

  private final AuthorizationServerProperties authorizationServerProperties;

  private static final String[] AUTH_WHITE_LIST = {
      "/actuator/health/**",
      "/swagger-resources/**",
      "/swagger-ui/**",
      "/swagger-ui.html",
      "/v3/api-docs/**",
      "/v3/api-docs",
      "/webjars/**",
      "/favicon.ico",
      "/oauth2/authorization/**",
      "/api/authorization/**"
  };

  @Bean
  public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
      throws Exception {
    OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
        OAuth2AuthorizationServerConfigurer.authorizationServer();
    http
        .cors(Customizer.withDefaults())
        .securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
        .with(authorizationServerConfigurer, (authorizationServer) ->
            authorizationServer
                .oidc(Customizer.withDefaults())
        )
        .authorizeHttpRequests((authorize) ->
            authorize
                .anyRequest().authenticated()
        ).exceptionHandling((exceptions) -> exceptions
            .defaultAuthenticationEntryPointFor(
                new LoginUrlAuthenticationEntryPoint("/login"),
                new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
            )
        );
    return http.build();
  }


  @Bean
  public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
      throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .cors(Customizer.withDefaults())
        .authorizeHttpRequests((authorize) -> authorize
            .requestMatchers(AUTH_WHITE_LIST).permitAll()
            .anyRequest().authenticated()
        )
        .formLogin(Customizer.withDefaults());

    return http.build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedHeaders(List.of("*"));
    config.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "HEAD", "PUT", "DELETE"));
    config.setAllowedOriginPatterns(List.of("http://localhost:8080*", "http://localhost:8082*", "http://auth-service:8082*",
        "http://user-service:8080*"));
    config.setAllowCredentials(true);
    source.registerCorsConfiguration("/**", config);
    return source;
  }

  @Bean
  public UserDetailsService userDetailsService(
      UserCredentialsRepository userCredentialsRepository) {
    return new CustomUserDetailsService(userCredentialsRepository);
  }

  @Bean
  public RegisteredClientRepository registeredClientRepository(
      ClientAuthRepository clientAuthRepository, ClientAuthMapper mapper) {
    return new CustomRegisteredClientRepositoryImpl(clientAuthRepository, mapper);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public Oauth2AccessTokenCustomizer oauth2AccessTokenCustomizer() {
    return new Oauth2AccessTokenCustomizer();
  }

  @Bean
  public JWKSource<SecurityContext> jwkSource() {
    RSAKey rsaKey = JwkUtils.generateRsa();
    JWKSet jwkSet = new JWKSet(rsaKey);
    return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
  }

  @Bean
  public AuthorizationServerSettings authorizationServerSettings() {
    return AuthorizationServerSettings.builder()
        .issuer(authorizationServerProperties.getIssuerUrl())
        .build();
  }

}
