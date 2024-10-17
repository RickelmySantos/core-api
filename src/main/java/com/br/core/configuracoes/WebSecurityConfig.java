package com.br.core.configuracoes;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@RequiredArgsConstructor
@AutoConfiguration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@Log4j2
public class WebSecurityConfig {

  @PostConstruct
  public void init() {
    WebSecurityConfig.log.info("LOADED >>>>> WebSecurityConfig");
  }

  @Bean
  MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
    return new MvcRequestMatcher.Builder(introspector);
  }

  // @ConditionalOnMissingBean
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc)
      throws Exception {

    WebSecurityConfig.log.info("LOADED >>>>> SecurityFilterChain");
    http.csrf(csrf -> csrf.disable());
    http.cors(cors -> Customizer.withDefaults());
    http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));

    http.authorizeHttpRequests(authorizeRequests -> {
      authorizeRequests.requestMatchers(mvc.pattern("/v3/api-docs")).permitAll()
          .requestMatchers(mvc.pattern("/swagger-resources/**")).permitAll()
          .requestMatchers(mvc.pattern("/swagger-ui/**")).permitAll()
          .requestMatchers(mvc.pattern("/swagger-ui/index.html**")).permitAll()
          .requestMatchers(mvc.pattern("/actuator/health")).permitAll()
          // APP
          .anyRequest().authenticated();
    });
    http.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> Customizer.withDefaults()));
    http.sessionManagement(
        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    return http.build();
  }
}
