package com.redcarepharmacy.githubscoringservice.infrastructure.controllers;

import com.redcarepharmacy.githubscoringservice.domain.GithubRepository;

import java.time.Clock;
import java.time.LocalDateTime;

public record GithubRepositoryResource(
        String name,
        double score,
        String language,
        LocalDateTime updatedAt,
        int stars,
        int forks) {

    public static GithubRepositoryResource fromDomain(GithubRepository githubRepository, int maxStars, int maxForks, Clock clock) {
        return new GithubRepositoryResource(
                githubRepository.name(),
                githubRepository.getScore(maxStars, maxForks, clock),
                githubRepository.language(),
                githubRepository.updatedAt(),
                githubRepository.stars(),
                githubRepository.forks()
        );
    }
}
