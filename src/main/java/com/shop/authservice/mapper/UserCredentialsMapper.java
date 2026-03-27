package com.shop.authservice.mapper;

import com.shop.authservice.dto.UserCredentialsDto;
import com.shop.authservice.entity.UserCredentials;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserCredentialsMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "hashedPassword", ignore = true)
  UserCredentials toUserCredentials(UserCredentialsDto source);
}
