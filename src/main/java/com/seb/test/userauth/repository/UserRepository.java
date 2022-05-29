package com.seb.test.userauth.repository;

import com.seb.test.userauth.model.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserRepository {
  private Map<String, UserEntity> failedCounter = new HashMap<>(Map.of("wei@weisebtest.com", UserEntity.builder().email("wei@weisebtest.com").password("password").build()));

  public UserEntity findByEmail(String username) {
    return failedCounter.get(username);
  }

  public void save(UserEntity user) {
    failedCounter.put(user.getEmail(), user);
  }
}
