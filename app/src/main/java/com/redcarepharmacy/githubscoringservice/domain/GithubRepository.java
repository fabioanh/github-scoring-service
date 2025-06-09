package com.redcarepharmacy.githubscoringservice.domain;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public record GithubRepository(String name, String language, LocalDateTime createdAt, LocalDateTime updatedAt,
                               int stars, int forks) {

    public double getScore(int maxStars, int maxForks, Clock clock) {
        double starsFactor = ((double) stars / (double) maxStars) * 0.33f;
        double forksFactor = ((double) forks / (double) maxForks) * 0.33f;
        double updatedAtFactor = ((double) updatedAt.toEpochSecond(ZoneOffset.UTC) / (double) LocalDateTime.now(clock).toEpochSecond(ZoneOffset.UTC)) * 0.33;
        return starsFactor + forksFactor + updatedAtFactor;
    }
}
