package com.server.pandascore.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("token")
public class Tokens {
    private String pandascore;
    private String slack;
    private String fcm;
}