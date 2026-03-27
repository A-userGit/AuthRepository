package com.shop.authservice.entity;

import com.shop.authservice.enums.TemporaryCodeType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "temp_codes")
public class TemporaryCode {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "temp_code_gen")
  @SequenceGenerator(name = "temp_code_gen", sequenceName = "temp_code_seq", allocationSize = 1)
  @Column(nullable = false)
  private Long id;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id", nullable = false)
  private UserCredentials userCredentials;

  @Column(nullable = false, name = "code")
  private String code;

  @Column(nullable = false, name = "type")
  @Enumerated(EnumType.STRING)
  private TemporaryCodeType type;

  @Column(nullable = false, name = "issued_at")
  private LocalDateTime issuedAt;
}
