package com.seb.test.userauth.controller;

import com.seb.test.userauth.model.response.ConfigDto;
import com.seb.test.userauth.model.response.UserLoginDto;
import com.seb.test.userauth.service.UserLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.seb.test.userauth.UserAuthApplication.API_VERSION_1;

@RestController(API_VERSION_1 + "/users")
@Slf4j
public class UserController {

  @Autowired
  AuthenticationManager authenticationManager;
  @Autowired
  UserLoginService userLoginService;

  /**
   * Authenticates user by filter.
   *
   * @return user if successful and login configurations
   */
  @PostMapping("/login")
  public ResponseEntity<UserLoginDto> authenticate() {
    log.info("/login authenticated and return user information");
    String email = SecurityContextHolder.getContext().getAuthentication().getName();

    return ResponseEntity.ok(userLoginService.getUser(email));
  }

  /**
   * Get login configuration
   *
   * @return user if successful and login configurations
   */
  @GetMapping("/config")
  public ResponseEntity<ConfigDto> config() {
    return ResponseEntity.ok(ConfigDto.from(3, 10));
  }
}
