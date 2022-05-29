package com.seb.test.userauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private MyBasicAuthenticationEntryPoint authenticationEntryPoint;

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  /*@Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    PasswordEncoder encoder =
            PasswordEncoderFactories.createDelegatingPasswordEncoder();
    auth
            .inMemoryAuthentication()
            .withUser("user")
            .password(encoder.encode("password"))
            .roles("USER")
            .and()
            .withUser("admin")
            .password(encoder.encode("admin"))
            .roles("USER", "ADMIN");
  }*/

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
            .antMatchers("/home")
            .authenticated()
            .and()
            .httpBasic()
            .authenticationEntryPoint(authenticationEntryPoint);
  }



/*
  @Override
  protected void configure(final HttpSecurity http)
          throws Exception {
    http.csrf().disable().authorizeRequests()
            //...
            .antMatchers(
                    HttpMethod.GET,
                    "/index*", "/login*", "/static/**", "/*.js", "/*.json", "/*.ico")
            .permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin().loginPage("/login").permitAll()
            //.loginProcessingUrl("/perform_login")
            .defaultSuccessUrl("/homepage.html",true)
            .failureUrl("/index.html?error=true");
    //...
  }
*/

  /*@Override
  protected void configure(HttpSecurity http) throws Exception {
    http
            .authorizeRequests()
            .anyRequest()
            .authenticated()
            .and()
            .httpBasic();
  }*/

  /*@Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeRequests(authz -> authz..anyRequest().authenticated()).httpBasic(Customizer.withDefaults());
    return http.build();
  }*/

  /*@Bean
  SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
            .cors()
            .and()
            .csrf()
            .disable()
            .authorizeRequests().antMatchers(WHITE_LIST_URLS).permitAll();
    return httpSecurity.build();
  }*/

  /*@Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.csrf().disable().authorizeRequests().antMatchers("/auth/*").permitAll().anyRequest().authenticated().and().exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
      Map<String, Object> responseMap = new HashMap<>();
      ObjectMapper mapper = new ObjectMapper();
      response.setStatus(401);
      responseMap.put("error", true);
      responseMap.put("message", "Unauthorized");
      response.setHeader("content-type", "application/json");
      String responseMsg = mapper.writeValueAsString(responseMap);
      response.getWriter().write(responseMsg);
    }).and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
  }*/
}
