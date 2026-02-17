package com.teksystems.gdit.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfiguration {

  @Bean
  public OpenAPI api() {
    return new OpenAPI().info(apiInfo()).tags(List.of(new Tag().name("FAFSA-applications").description("FAFSA Applications at GDIT")));
  }

  private Info apiInfo() {
    return new Info().title("FAFSA-applications").description("FAFSA Applications at GDIT").version("1.0.0");
  }
}
