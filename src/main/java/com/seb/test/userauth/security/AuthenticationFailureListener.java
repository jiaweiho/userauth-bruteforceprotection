package com.seb.test.userauth.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

  @Resource(name = "bruteForceProtectionService")
  private DefaultBruteForceProtectionService bruteForceProtectionService;

  @Override
  public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
    String username = event.getAuthentication().getName();
    log.info("********* login failed for user {} ", username);
    bruteForceProtectionService.registerLoginFailure(username);

  }
}
