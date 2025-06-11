package com.redcarepharmacy.githubscoringservice.infrastructure.config;

import com.redcarepharmacy.githubscoringservice.App;
import com.redcarepharmacy.githubscoringservice.application.annotations.DomainService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.time.Clock;

@Configuration
@EnableConfigurationProperties(GithubRestClientProperties.class)
@ComponentScan(
        basePackageClasses = {App.class},
        includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {DomainService.class})})
public class AppConfig {

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone(); // Or Clock.systemUTC() for UTC
    }

    @Bean
    public RestClient githubRestClient(GithubRestClientProperties clientProperties) {
        return RestClient.builder()
                .requestFactory(new JdkClientHttpRequestFactory())
                .baseUrl(clientProperties.baseUrl())
                .build();
    }
}
