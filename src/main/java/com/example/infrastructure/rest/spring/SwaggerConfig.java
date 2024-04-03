package com.example.infrastructure.rest.spring;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class SwaggerConfig {

  @Bean
  GroupedOpenApi publicApi() {
    return GroupedOpenApi.builder().group("public-apis").pathsToMatch("/**").build();
  }

  @Bean
  OpenAPI customOpenApi() {
    return new OpenAPI().info(new Info().title("API title").version("API version"));
  }
}
