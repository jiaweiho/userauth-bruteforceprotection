package com.seb.test.userauth.model.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConfigDto {
  Integer maxAttempts;
  Integer blockDuration;

  public static ConfigDto from(Integer maxAttempts, Integer blockDuration) {
    return ConfigDto.builder().maxAttempts(maxAttempts).blockDuration(blockDuration).build();
  }
}
