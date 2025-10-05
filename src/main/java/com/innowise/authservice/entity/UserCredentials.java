package com.innowise.authservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "user_credentials")
public class UserCredentials {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_credentials_gen")
  @SequenceGenerator(name = "user_credentials_gen", sequenceName = "user_credentials_seq", allocationSize = 1)
  @Column(nullable = false)
  private Long id;

  @Column(nullable = false, name = "user_id")
  private long userId;

  @Column(nullable = false, unique = true, name = "login")
  private String login;

  @Column(nullable = false, name = "hashed_password")
  private byte[] hashedPassword;
}
