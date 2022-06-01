package com.seb.test.userauth.security;

import com.seb.test.userauth.model.entities.UserLoginEntity;
import com.seb.test.userauth.repository.UserLoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Default protection identifying the request by username.
 */
@Service("bruteForceProtectionService")
public class DefaultBruteForceProtectionService implements BruteForceProtectionService {

  @Value("${userauth.security.interval.calculate.attempt}")
  private Long intervalCalculateAttempt;

  @Value("${userauth.security.max.failed.logins}")
  private Integer maxFailedLogins;

  @Value("${userauth.security.block.duration}")
  private Integer blockDuration;

  @Autowired
  private UserLoginRepository userLoginRepository;

  @Override
  @Transactional
  public void registerLoginFailure(String username) {

    UserLoginEntity user = getUser(username);
    if (user != null && !user.isLoginDisabled()) {
      var now = LocalDateTime.now();

      if (isResetFailedAttempt(user, now)) {
        resetFailedCounter(user.getUsername());
      }
      if (isBruteForceAttack(username, now)) {
        user = user.toBuilder().loginDisabled(true).build();
      }

      userLoginRepository.save(user.toBuilder().failedLoginAttempts(user.getFailedLoginAttempts() + 1).build());
    } else if (user != null && user.getLastFailedAt().plusSeconds(blockDuration).isAfter(LocalDateTime.now())) {
      userLoginRepository.save(UserLoginEntity.from(username));
    } else {
      userLoginRepository.save(UserLoginEntity.from(username));
    }
  }

  private boolean isResetFailedAttempt(UserLoginEntity user, LocalDateTime now) {
    return user.getStartFailedAt() != null && now.isAfter(user.getStartFailedAt().plusSeconds(intervalCalculateAttempt));
  }

  @Override
  public void resetFailedCounter(String username) {
    UserLoginEntity user = getUser(username);
    if (user != null) {
      user.setFailedLoginAttempts(0);
      user.setLoginDisabled(false);
      userLoginRepository.save(user);
    }
  }

  @Override
  public boolean isBruteForceAttack(String username, LocalDateTime now) {
    UserLoginEntity user = getUser(username);
    if (user != null) {
      LocalDateTime failedAt = user.getStartFailedAt();
      return failedAt != null && now.isBefore(failedAt.plusSeconds(intervalCalculateAttempt)) && user.getFailedLoginAttempts() + 1 >= maxFailedLogins;
    }
    return false;
  }

  private UserLoginEntity getUser(final String username) {
    return userLoginRepository.findByUsername(username);
  }
}
