package com.shop.authservice.repository;

import com.shop.authservice.entity.TemporaryCode;
import com.shop.authservice.enums.TemporaryCodeType;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TemporaryCodeRepository extends JpaRepository<TemporaryCode, Long> {

  @Query(value = "from TemporaryCode as tc where tc.type = :type and tc.issuedAt > :dateTime and tc.code = :code")
  TemporaryCode getCodeForUser(String code, TemporaryCodeType type, LocalDateTime dateTime);
}
