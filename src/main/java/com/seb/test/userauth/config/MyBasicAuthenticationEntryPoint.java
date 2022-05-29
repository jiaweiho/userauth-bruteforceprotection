package com.seb.test.userauth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class MyBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {
  @Override
  public void commence(
          HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx)
          throws IOException {
    response.addHeader("WWW-Authenticate", "Basic realm=\"" + getRealmName() + "\"");
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    Map<String, Object> responseMap = new HashMap<>();
    ObjectMapper mapper = new ObjectMapper();
    response.setStatus(401);
    responseMap.put("error", true);
    responseMap.put("message", "Unauthorized");
    response.setHeader("content-type", "application/json");
    String responseMsg = mapper.writeValueAsString(responseMap);
    response.getWriter().write(responseMsg);
  }

  @Override
  public void afterPropertiesSet() {
    setRealmName("Wei");
    super.afterPropertiesSet();
  }
}
