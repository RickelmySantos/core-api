package com.br.core.configuracoes;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.Ordered;
import org.springframework.scheduling.annotation.EnableAsync;

@AutoConfiguration
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@EnableAsync
@EnableConfigurationProperties
@ConfigurationPropertiesScan("com.br.core.properties")
@ComponentScan(basePackages = {"com.br"})
@Log4j2
public class CoreAutoConfig {



  @PostConstruct
  public void init() {
    CoreAutoConfig.log.info("LOADED >>>> CoreAutoConfig");

  }

}
