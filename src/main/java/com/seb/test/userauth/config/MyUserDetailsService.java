package com.seb.test.userauth.config;

import com.seb.test.userauth.model.entities.UserEntity;
import com.seb.test.userauth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
  @Autowired
  UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    final UserEntity customer = userRepository.findByEmail(email);
    if (customer == null) {
      throw new UsernameNotFoundException(email);
    }
    boolean enabled = !customer.isAccountVerified();

    PasswordEncoder encoder =
            PasswordEncoderFactories.createDelegatingPasswordEncoder();
    UserDetails user = User.withUsername(customer.getEmail())
            .password(encoder.encode(customer.getPassword()))
            .disabled(customer.isLoginDisabled())
            .authorities("USER").build();
    return user;
  }
}
