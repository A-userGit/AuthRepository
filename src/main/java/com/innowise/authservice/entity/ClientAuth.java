package com.innowise.authservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "client_auth")
public class ClientAuth {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_auth_gen")
  @SequenceGenerator(name = "client_auth_gen", sequenceName = "client_auth_seq", allocationSize = 1)
  @Column(nullable = false)
  private Long id;

  @Column(nullable = false, name = "client_name")
  private String clientName;

  @Column(nullable = false, name = "client_id")
  private String clientId;

  @Column(nullable = false, name = "client_secret")
  private String clientSecret;

  @Column(name = "scope")
  private String scope;

  @Column(name = "redirect_uri")
  private String redirectUri;

  @Column(name = "post_logout_redirect_uri")
  private String postLogoutRedirectUri;

  @Column(nullable = false, name = "is_public")
  private boolean isPublic;
}