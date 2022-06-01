package com.seb.test.userauth.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "user_login")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  private String username;

  @Column(name = "login_disabled")
  private boolean loginDisabled = false;

  @Column(name = "failed_login_attempts")
  private int failedLoginAttempts = 0;

  @Column(name = "start_failed_at")
  private LocalDateTime startFailedAt = LocalDateTime.MIN;

  @Column(name = "last_failed_at")
  private LocalDateTime lastFailedAt = LocalDateTime.MIN;

  public static UserLoginEntity from(String email) {
    return UserLoginEntity.builder().username(email).build();
  }

}
