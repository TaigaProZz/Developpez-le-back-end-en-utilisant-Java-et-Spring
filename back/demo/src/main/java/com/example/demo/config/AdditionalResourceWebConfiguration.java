package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AdditionalResourceWebConfiguration implements WebMvcConfigurer {
  /**
   * Configures resource handlers to map URL patterns to specific resource locations.
   * This method is used to serve static resources from a specified directory.
   *
   * @param registry the {@link ResourceHandlerRegistry} used to add resource handlers
   *                 and their corresponding resource locations
   */
  @Override
  public void addResourceHandlers(final ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/api/images/**").addResourceLocations("file:uploads/images/");
  }
}