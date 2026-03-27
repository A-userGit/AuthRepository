package com.shop.authservice.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.List;
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

  @Column(nullable = false, unique = true, name = "login")
  private String login;

  @Column(nullable = false, name = "hashed_password")
  private byte[] hashedPassword;

  @OneToMany(mappedBy = "userCredentials", cascade = CascadeType.ALL)
  private List<TemporaryCode> temporaryCodes;
}
