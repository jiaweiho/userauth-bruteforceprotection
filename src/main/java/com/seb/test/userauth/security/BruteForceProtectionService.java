package com.seb.test.userauth.security;

public interface BruteForceProtectionService {
  public void registerLoginFailure(String email);

  public void resetFailedCounter(String email);

  public boolean isBruteForceAttack(String email);
}
