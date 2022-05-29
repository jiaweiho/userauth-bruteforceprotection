package com.seb.test.userauth.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class UserEntity {
  private boolean loginDisabled;
  private boolean accountVerified;
  private int failedLoginAttempts;
  private String email;
  private String password;
  private List<String> authorities;
}
