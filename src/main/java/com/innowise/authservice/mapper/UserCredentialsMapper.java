package com.innowise.authservice.mapper;

import com.innowise.authservice.dto.UserCredentialsDto;
import com.innowise.authservice.entity.UserCredentials;
import java.nio.charset.StandardCharsets;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Mapper(componentModel = "spring")
public interface UserCredentialsMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "hashedPassword", source = "password", qualifiedByName = "hashPassword")
  UserCredentials toUserCredentials(UserCredentialsDto source);

  @Named("hashPassword")
  default byte[] hashPassword(String source) {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    return encoder.encode(source).getBytes(StandardCharsets.UTF_8);
  }
}
