package com.shop.authservice.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomRegisteredClientRepository extends RegisteredClientRepository {

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  void evictClient(String clientId);

}
