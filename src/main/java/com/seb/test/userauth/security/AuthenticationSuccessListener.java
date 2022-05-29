package com.seb.test.userauth.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

  @Resource(name = "bruteForceProtectionService")
  private DefaultBruteForceProtectionService bruteForceProtectionService;

  @Override
  public void onApplicationEvent(AuthenticationSuccessEvent event) {
    String username = event.getAuthentication().getName();
    log.info("********* login successful for user {} ", username);
    bruteForceProtectionService.resetFailedCounter(username);
  }
}
