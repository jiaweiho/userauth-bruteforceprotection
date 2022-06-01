package com.seb.test.userauth.config;

import com.seb.test.userauth.model.entities.UserEntity;
import com.seb.test.userauth.model.entities.UserLoginEntity;
import com.seb.test.userauth.repository.UserLoginRepository;
import com.seb.test.userauth.repository.UserRepository;
import com.seb.test.userauth.security.BruteForceProtectionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
        "userauth.security.max.failed.logins=3",
        "userauth.security.block.duration=2",
        "userauth.security.interval.calculate.attempt=60",
})
class WebSecurityConfigIntegrationTest {

  private static String USERNAME_1 = "wei@weisebtest.com";
  private static String USERNAME_2 = "wei2@weisebtest.com";
  private static String PASSWORD = "password";
  private static String WRONG_PASSWORD = "wrongpassword";

  @Autowired
  TestRestTemplate restTemplate;
  @Autowired
  BruteForceProtectionService bruteForceProtectionService;
  @Autowired
  UserLoginRepository userLoginRepository;
  @Autowired
  UserRepository userRepository;

  URL base;
  @LocalServerPort
  int port;

  @BeforeEach
  public void setUp() throws MalformedURLException {
    restTemplate = new TestRestTemplate();
    base = new URL("http://localhost:" + port);
    // Add failed login
    userLoginRepository.save(UserLoginEntity.builder().username(USERNAME_1).failedLoginAttempts(0).startFailedAt(LocalDateTime.now()).build());

    // Add customers
    userRepository.save(UserEntity.builder().username(USERNAME_1).password(PASSWORD).build());
    userRepository.save(UserEntity.builder().username("wei2@weisebtest.com").password(PASSWORD).build());
  }

  @AfterEach
  public void cleanUp() {
    userLoginRepository.deleteAll();
    userLoginRepository.flush();
  }

  @Test
  public void whenLoggedUserRequestsHomePage_ThenSuccess()
          throws IllegalStateException {
    ResponseEntity<String> response =
            restTemplate.withBasicAuth(USERNAME_1, PASSWORD).getForEntity(base.toString() + "/home", String.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void whenLoggedUserRequestsHomePage_ThenReset()
          throws IllegalStateException {
    var response =
            restTemplate.withBasicAuth(USERNAME_1, WRONG_PASSWORD).postForEntity(base.toString() + "/login", HttpEntity.EMPTY, String.class);
    var user1 = userLoginRepository.findByUsername(USERNAME_1);
    assertEquals(1, user1.getFailedLoginAttempts());

    response =
            restTemplate.withBasicAuth(USERNAME_1, WRONG_PASSWORD).postForEntity(base.toString() + "/login", HttpEntity.EMPTY, String.class);
    user1 = userLoginRepository.findByUsername(USERNAME_1);
    assertEquals(2, user1.getFailedLoginAttempts());

    response =
            restTemplate.withBasicAuth(USERNAME_1, PASSWORD).postForEntity(base.toString() + "/login", HttpEntity.EMPTY, String.class);
    user1 = userLoginRepository.findByUsername(USERNAME_1);
    assertEquals(0, user1.getFailedLoginAttempts());

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void whenLoggedUserRequestsHomePage_ThenMaxBlockEvenIfSuccessful()
          throws IllegalStateException {
    var response =
            restTemplate.withBasicAuth(USERNAME_1, WRONG_PASSWORD).postForEntity(base.toString() + "/login", HttpEntity.EMPTY, String.class);
    var user1 = userLoginRepository.findByUsername(USERNAME_1);
    assertEquals(1, user1.getFailedLoginAttempts());

    response =
            restTemplate.withBasicAuth(USERNAME_1, WRONG_PASSWORD).postForEntity(base.toString() + "/login", HttpEntity.EMPTY, String.class);
    user1 = userLoginRepository.findByUsername(USERNAME_1);
    assertEquals(2, user1.getFailedLoginAttempts());

    response =
            restTemplate.withBasicAuth(USERNAME_1, WRONG_PASSWORD).postForEntity(base.toString() + "/login", HttpEntity.EMPTY, String.class);
    user1 = userLoginRepository.findByUsername(USERNAME_1);
    assertEquals(3, user1.getFailedLoginAttempts());

    response =
            restTemplate.withBasicAuth(USERNAME_1, PASSWORD).postForEntity(base.toString() + "/login", HttpEntity.EMPTY, String.class);
    user1 = userLoginRepository.findByUsername(USERNAME_1);
    assertEquals(0, user1.getFailedLoginAttempts());

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void whenUserWithWrongCredentials_thenUnauthorizedPage() {
    ResponseEntity<String> response =
            restTemplate.withBasicAuth(USERNAME_2, WRONG_PASSWORD).getForEntity(base.toString() + "/home", String.class);

    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
  }

  @Test
  @Disabled
  public void whenMissingUser() {
    ResponseEntity<String> response =
            restTemplate.withBasicAuth("wei3@weisebtest.com", "wrongpassword").getForEntity(base.toString() + "/home", String.class);

    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
  }
}