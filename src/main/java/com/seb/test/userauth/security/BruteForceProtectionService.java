package com.seb.test.userauth.security;

import java.time.LocalDateTime;

/**
 * For extending different implementations of protection against brute force attack
 * TODO: Another improvement would be to have an IP tracker instead of username
 */
public interface BruteForceProtectionService {
  public void registerLoginFailure(String username);

  public void resetFailedCounter(String username);

  public boolean isBruteForceAttack(String username, LocalDateTime now);
}
