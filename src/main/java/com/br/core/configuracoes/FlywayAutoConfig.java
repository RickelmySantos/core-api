package com.br.core.configuracoes;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.flywaydb.core.api.FlywayException;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@AutoConfiguration
@Log4j2
public class FlywayAutoConfig {


  @PostConstruct
  public void init() {
    FlywayAutoConfig.log.info("LOADED >>>> FlywayAutoConfig");
  }

  @Bean
  @Profile("!test & !deploy")
  public FlywayMigrationStrategy developmentConfig() {
    return flyway -> {
      try {
        flyway.migrate();
      } catch (FlywayException e) {
        flyway.clean();
        flyway.repair();
        flyway.migrate();
      }
    };
  }

}
