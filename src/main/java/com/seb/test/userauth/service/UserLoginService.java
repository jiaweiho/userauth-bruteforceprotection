package com.seb.test.userauth.service;

import com.seb.test.userauth.model.response.UserLoginDto;
import com.seb.test.userauth.repository.UserLoginRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserLoginService {
  private UserLoginRepository userLoginRepository;

  public UserLoginDto getUser(String username) {
    return UserLoginDto.from(userLoginRepository.findByUsername(username));
  }
}
