package com.wanted.budgetmanagement.global.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;


@Getter
@Component
@ConfigurationProperties("jwt")
public class JwtConfig {

    public static final long ACCESS_TOKEN_EXPIRATION_MS = Duration.ofHours(1).toMillis();
    public static final long REFRESH_TOKEN_EXPIRATION_MS = Duration.ofDays(30).toMillis();

    @Value("${JWT_ISSUER}")
    private String jwtIssuer;

    @Value("${JWT_SECRET_KEY}")
    private String jwtKey;

}
