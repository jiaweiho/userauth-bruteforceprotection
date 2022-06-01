package com.seb.test.userauth.security;

import com.seb.test.userauth.SpringBootComponentTest;
import com.seb.test.userauth.model.entities.UserLoginEntity;
import com.seb.test.userauth.repository.UserLoginRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DefaultBruteForceProtectionServiceTest extends SpringBootComponentTest {

  private static String USERNAME_1 = "wei@webtest.com";

  @Autowired
  private BruteForceProtectionService bruteForceProtectionService;

  @Autowired
  private UserLoginRepository repository;

  @Captor
  private ArgumentCaptor<UserLoginEntity> userCaptor;

  @Mock
  private Clock clock;

  @BeforeEach
  public void setUp() {
    repository.save(getDefaultUser());
  }

  @AfterEach
  public void cleanUp() {
    repository.deleteAll();
  }

  @Test
  void testFailedAttempts() {
    bruteForceProtectionService.registerLoginFailure(USERNAME_1);

    var result = repository.findByUsername(USERNAME_1);
    //verify(repository).save(userCaptor.capture());
    assertEquals(1, result.getFailedLoginAttempts());
  }

  @Test
  void testMaxFailedAttempts() {
    var username = repository.save(repository.findByUsername(USERNAME_1).toBuilder().failedLoginAttempts(1).build()).getUsername();
    bruteForceProtectionService.registerLoginFailure(username);
    var result = repository.findByUsername(username);
    //verify(repository).save(userCaptor.capture());
    assertEquals(2, result.getFailedLoginAttempts());
    assertTrue(result.isLoginDisabled());
  }

  @Test
  @Disabled
  void testResetFailedAttemptsAfterInterval() {
    repository.save(repository.findByUsername(USERNAME_1).toBuilder().failedLoginAttempts(1).build());
    bruteForceProtectionService.registerLoginFailure(USERNAME_1);

    bruteForceProtectionService.registerLoginFailure(USERNAME_1);

    var result = repository.findByUsername(USERNAME_1);
    //verify(repository).save(userCaptor.capture());
    assertEquals(0, result.getFailedLoginAttempts());
    assertFalse(result.isLoginDisabled());
  }

  private UserLoginEntity getDefaultUser() {
    return UserLoginEntity.builder().id(UUID.randomUUID()).username(USERNAME_1).failedLoginAttempts(0).startFailedAt(LocalDateTime.now()).build();
  }
}