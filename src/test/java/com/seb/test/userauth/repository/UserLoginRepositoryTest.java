package com.seb.test.userauth.repository;

import com.seb.test.userauth.SpringBootComponentTest;
import com.seb.test.userauth.model.entities.UserLoginEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

class UserLoginRepositoryTest extends SpringBootComponentTest {

  @Autowired
  UserLoginRepository repository;

  @Test
  void saveAndRetrieve() {
    UserLoginEntity entity = UserLoginEntity.builder().username("wei").build();
    repository.saveAll(List.of(entity));
    UserLoginEntity fromRepository = repository.findByUsername(entity.getUsername());
    assertThat(fromRepository, is(entity));
  }

}