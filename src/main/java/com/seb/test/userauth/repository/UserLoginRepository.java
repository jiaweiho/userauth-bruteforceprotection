package com.seb.test.userauth.repository;

import com.seb.test.userauth.model.entities.UserLoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLoginRepository extends JpaRepository<UserLoginEntity, Long>, JpaSpecificationExecutor<UserLoginEntity> {
  public UserLoginEntity findByUsername(String email);
}
