package com.innowise.authservice.repository;

import com.innowise.authservice.entity.ClientAuth;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientAuthRepository extends JpaRepository<ClientAuth, Long> {
  @Query("from ClientAuth as ca where ca.clientId = :clientId")
  Optional<ClientAuth> findByClientId(String clientId);

  @Modifying
  @Query("delete from ClientAuth as ca where ca.clientId = :clientId")
  void deleteByClientId(String clientId);
}
