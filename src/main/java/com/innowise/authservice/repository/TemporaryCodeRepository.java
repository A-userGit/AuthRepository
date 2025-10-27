package com.innowise.authservice.repository;

import com.innowise.authservice.entity.TemporaryCode;
import com.innowise.authservice.enums.TemporaryCodeType;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TemporaryCodeRepository extends JpaRepository<TemporaryCode, Long> {

  @Query(value = "from TemporaryCode as tc where tc.type = :type and tc.issuedAt > :dateTime and tc.code = :code")
  TemporaryCode getCodeForUser(String code, TemporaryCodeType type, LocalDateTime dateTime);
}
