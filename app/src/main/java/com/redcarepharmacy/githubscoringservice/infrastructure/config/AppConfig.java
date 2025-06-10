package com.redcarepharmacy.githubscoringservice.infrastructure.config;

import com.redcarepharmacy.githubscoringservice.application.annotations.DomainService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import java.time.Clock;

@Configuration
@ComponentScan(includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {DomainService.class})})
public class AppConfig {

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone(); // Or Clock.systemUTC() for UTC
    }


}
