package com.br.core.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = true, ignoreInvalidFields = true)
public class CoreProperties {
  private String baseUrl;
}
