package com.seb.test.userauth.config;

import com.seb.test.userauth.model.entities.UserEntity;
import com.seb.test.userauth.model.entities.UserLoginEntity;
import com.seb.test.userauth.repository.UserLoginRepository;
import com.seb.test.userauth.repository.UserRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MyUserDetailsService implements UserDetailsService {
  @Autowired
  UserLoginRepository userLoginRepository;

  @Autowired
  UserRepository userRepository;

  /**
   * This will loadUserByName and only if user exist will the authentication be successful.
   *
   * @param email the username identifying the user whose data is required.
   * @return
   * @throws UsernameNotFoundException
   */
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    final UserEntity customer = userRepository.findByUsername(email);
    if (customer == null) {
      throw new UsernameNotFoundException(email);
    }

    UserLoginEntity userLogin = userLoginRepository.findByUsername(email);
    PasswordEncoder encoder =
            PasswordEncoderFactories.createDelegatingPasswordEncoder();
    UserDetails user = User.withUsername(customer.getUsername())
            .password(encoder.encode(customer.getPassword()))
            .disabled(userLogin.isLoginDisabled())
            .authorities("USER").build();
    return user;
  }
}
