package com.shop.authservice.mapper;

import com.shop.authservice.entity.ClientAuth;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClientAuthMapper {

  @Mapping(target = "scope", source = "scopes", qualifiedByName = "getScopes")
  ClientAuth toClientAuth(RegisteredClient source);

  @Named("getScopes")
  default String getScopes(Set<String> source) {
    return source.stream().reduce("", (allScopes, scope) -> allScopes + " " + scope);
  }

}
