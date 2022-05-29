package com.seb.test.userauth.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserController {

  @Autowired
  AuthenticationManager authenticationManager;

  /*@PostMapping("/login")
  public boolean authenticate() {
    String email = userRequest.getEmail();
    try {
      log.info("Checking...");
      log.info(email);
      log.info(userRequest.getPassword());
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, userRequest.getPassword()));
      // String token = jwtTokenProvider.createToken(username,
      // this.users.findByEmail(username).getRoles());
      log.info("AUTHENTICATED");
      Map<Object, Object> model = new HashMap<>();
      model.put("email", email);
      // model.put("token", token);
      return true;
    } catch (AuthenticationException e) {
      // Show number of attempts left and when it's maximum reached then return
      throw new RuntimeException(e);
    }
  }*/
}
