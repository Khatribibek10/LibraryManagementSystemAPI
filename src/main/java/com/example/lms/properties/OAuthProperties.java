package com.example.lms.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "ewallet.oauth")
@Getter
@Setter
public class OAuthProperties {
    private String url;
    private String username;
    private String password;
}
