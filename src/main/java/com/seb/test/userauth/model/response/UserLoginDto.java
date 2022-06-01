package com.seb.test.userauth.model.response;

import com.seb.test.userauth.model.entities.UserLoginEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserLoginDto implements Serializable {
  String username;

  boolean loginDisabled;

  int failedLoginAttempts;

  public static UserLoginDto from(UserLoginEntity user) {
    return UserLoginDto.builder().username(user.getUsername()).failedLoginAttempts(user.getFailedLoginAttempts()).loginDisabled(user.isLoginDisabled()).build();
  }
}
