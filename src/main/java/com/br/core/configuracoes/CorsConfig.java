package com.br.core.configuracoes;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@AutoConfiguration
@Log4j2
public class CorsConfig implements WebMvcConfigurer {


  @PostConstruct
  public void init() {
    CorsConfig.log.info("LOADED >>>> CorsConfig");
  }

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOriginPatterns("*").allowedMethods("*")
            .allowedHeaders("*").allowCredentials(true);
      }
    };
  }
}
