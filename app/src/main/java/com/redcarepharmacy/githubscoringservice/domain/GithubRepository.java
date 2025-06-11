package com.redcarepharmacy.githubscoringservice.domain;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public record GithubRepository(String name, String language, Instant createdAt, Instant updatedAt,
                               int stars, int forks) {

    public double getScore(Integer maxStars, Integer maxForks, Clock clock) {
        int numFactors = 1;
        if (maxStars != null && maxStars > 0) {
            numFactors++;
        }
        if (maxForks != null && maxForks > 0) {
            numFactors++;
        }
        double splitFactor = 1.0 / numFactors;
        double starsFactor = 0.0;
        double forksFactor = 0.0;

        if (maxStars != null && maxStars > 0) {
            starsFactor = ((double) stars / (double) maxStars) * splitFactor;
        }

        if (maxForks != null && maxForks > 0) {
            forksFactor = ((double) forks / (double) maxForks) * splitFactor;
        }

        var lastUpdatedAt = updatedAt != null ? updatedAt : createdAt;
        double updatedAtFactor = ((double) lastUpdatedAt.getEpochSecond() / (double) LocalDateTime.now(clock).toEpochSecond(ZoneOffset.UTC)) * splitFactor;
        return starsFactor + forksFactor + updatedAtFactor;
    }
}
