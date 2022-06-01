package com.seb.test.userauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Clock;

@SpringBootApplication
public class UserAuthApplication {

  public static final String API_VERSION_1 = "/api/v1";

  public static void main(String[] args) {
    SpringApplication.run(UserAuthApplication.class, args);
  }

  @Bean
  public Clock clock() {
    return Clock.systemDefaultZone();
  }
}
