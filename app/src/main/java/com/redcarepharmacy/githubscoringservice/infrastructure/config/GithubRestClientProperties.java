package com.redcarepharmacy.githubscoringservice.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "github.rest.client")
public record GithubRestClientProperties(
        String baseUrl,
        String token
) {
}
