package com.seb.test.userauth.controller;

import com.seb.test.userauth.model.response.HomeData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class HomeController {
  @GetMapping("/home")
  public HomeData getHomeData() {
    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    try {
      log.info("Checking...");
      log.info(email);
      log.info("AUTHENTICATED");
      Map<Object, Object> model = new HashMap<>();
      model.put("email", email);
      return new HomeData("Home of " + email, "Great content for user to stay in this app");
    } catch (AuthenticationException e) {
      throw new RuntimeException(e);
    }
  }

}
