package com.shop.authservice.repository;

import com.shop.authservice.entity.UserCredentials;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCredentialsRepository extends JpaRepository<UserCredentials, Long> {
  @Query("from UserCredentials as uc where uc.login = :login")
  Optional<UserCredentials> findByLogin(String login);
}
