package com.br.core.configuracoes;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@AutoConfiguration
@EnableJpaAuditing
@ConditionalOnProperty("spring.datasource.url")
@Log4j2
public class JPAAutoConfig {


  @PostConstruct
  public void init() {
    JPAAutoConfig.log.info("LOADED >>>> JPAAutoConfig");
  }


}
