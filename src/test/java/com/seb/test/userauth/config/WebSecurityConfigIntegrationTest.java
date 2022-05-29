package com.seb.test.userauth.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WebSecurityConfigIntegrationTest {
  @Autowired
  TestRestTemplate restTemplate;
  URL base;
  @LocalServerPort
  int port;

  @BeforeEach
  public void setUp() throws MalformedURLException {
    restTemplate = new TestRestTemplate("user", "password");
    base = new URL("http://localhost:" + port);
  }

  @Test
  public void whenLoggedUserRequestsHomePage_ThenSuccess()
          throws IllegalStateException {
    ResponseEntity<String> response =
            restTemplate.withBasicAuth("wei@weisebtest.com", "password").getForEntity(base.toString() + "/home", String.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody().contains("user"));
  }

  @Test
  public void whenUserWithWrongCredentials_thenUnauthorizedPage() {
    ResponseEntity<String> response =
            restTemplate.withBasicAuth("wei@weisebtest.com", "wrongpassword").getForEntity(base.toString() + "/home", String.class);

    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
  }
}