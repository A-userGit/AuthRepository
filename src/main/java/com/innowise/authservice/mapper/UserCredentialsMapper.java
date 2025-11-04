package com.innowise.authservice.mapper;

import com.innowise.authservice.dto.UserCredentialsDto;
import com.innowise.authservice.entity.UserCredentials;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserCredentialsMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "hashedPassword", ignore = true)
  UserCredentials toUserCredentials(UserCredentialsDto source);
}
