package com.example.demo.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  /**
   * Configures and returns an OpenAPI bean with API metadata and server details.
   * This method sets up custom information for the API, including title, version,
   * description, and server URL configurations.
   *
   * @return an instance of {@link OpenAPI} configured with API metadata and server details
   */
  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
            .info(new Info()
                    .title("Mon API")
                    .version("1.0")
                    .description("Documentation de l'API"))
                    .components(new Components().addSecuritySchemes("bearerAuth",
                    new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));

  }
}
