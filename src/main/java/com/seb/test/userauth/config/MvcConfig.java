package com.seb.test.userauth.config;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//@EnableWebMvc
//@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

  @Override
  public void addResourceHandlers(
          ResourceHandlerRegistry registry) {

    registry.addResourceHandler("/static/**")
            .addResourceLocations("/WEB-INF/view/angular/build/static/");
    registry.addResourceHandler("/*.js")
            .addResourceLocations("/WEB-INF/view/angular/build/");
    registry.addResourceHandler("/*.json")
            .addResourceLocations("/WEB-INF/view/angular/build/");
    registry.addResourceHandler("/*.ico")
            .addResourceLocations("/WEB-INF/view/angular/build/");
    registry.addResourceHandler("/index.html")
            .addResourceLocations("/WEB-INF/view/angular/build/index.html");
  }
}
