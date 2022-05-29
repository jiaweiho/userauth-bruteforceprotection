package com.seb.test.userauth.model.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserRequest {
  private String email;
  private String password;
}
