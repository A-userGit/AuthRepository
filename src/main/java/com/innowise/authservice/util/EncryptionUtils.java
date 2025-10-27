package com.innowise.authservice.util;

import java.nio.charset.StandardCharsets;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncryptionUtils {

  public static byte[] getHash(String value) {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    return encoder.encode(value).getBytes(StandardCharsets.UTF_8);
  }

}
