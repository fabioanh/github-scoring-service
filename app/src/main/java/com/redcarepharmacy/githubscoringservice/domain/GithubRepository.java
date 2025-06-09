package com.redcarepharmacy.githubscoringservice.domain;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class GithubRepository {
    private final String name;
    private final String language;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final int stars;
    private final int forks;

    public GithubRepository(String name, String language, LocalDateTime createdAt, LocalDateTime updatedAt, int stars, int forks) {
        this.name = name;
        this.language = language;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.stars = stars;
        this.forks = forks;
    }

    public double getScore(int maxStars, int maxForks, Clock clock) {
        double starsFactor = ((double) stars / (double) maxStars) * 0.33f;
        double forksFactor = ((double) forks / (double) maxForks) * 0.33f;
        double updatedAtFactor = ((double) updatedAt.toEpochSecond(ZoneOffset.UTC) / (double) LocalDateTime.now(clock).toEpochSecond(ZoneOffset.UTC)) * 0.33;
        return starsFactor + forksFactor + updatedAtFactor;
    }

    public String getName() {
        return name;
    }

    public String getLanguage() {
        return language;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public int getStars() {
        return stars;
    }

    public int getForks() {
        return forks;
    }
}
