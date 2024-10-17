package com.br.core.configuracoes;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@ConditionalOnProperty("openapi.title")
@Log4j2
public class SwaggerConfig {

  @Value("${openapi.title}")
  private String apiTitle;

  @Value("${openapi.description}")
  private String apiDescription;

  @Value("${openapi.version}")
  private String apiVersion;

  @Value("${openapi.contact.name}")
  private String apiContactName;

  @Value("${openapi.contact.url}")
  private String apiContactUrl;

  @Value("${openapi.contact.email}")
  private String apiContactEmail;

  @Value("${app.base-url:http://localhost:8080}")
  private String serverUrl;

  @Value("${server.servlet.context-path}")
  private String contextPath;

  @PostConstruct
  public void init() {
    SwaggerConfig.log.info("LOADED >>>>> SwaggerConfig");
  }

  @Bean
  public OpenAPI myOpenAPI() {
    return new OpenAPI().info(this.getInfo()).servers(List.of(this.getServer()));
  }

  private Server getServer() {
    Server server = new Server();
    server.setUrl(this.serverUrl + this.contextPath);
    return server;
  }

  private Info getInfo() {
    Contact contact = new Contact();
    contact.setName(this.apiContactName);
    contact.setEmail(this.apiContactEmail);
    contact.setUrl(this.apiContactUrl);

    return new Info().title(this.apiTitle).description(this.apiDescription).version(this.apiVersion)
        .contact(contact);
  }
}

