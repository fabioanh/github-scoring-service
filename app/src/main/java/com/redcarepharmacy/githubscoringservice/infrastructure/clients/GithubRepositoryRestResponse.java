package com.redcarepharmacy.githubscoringservice.infrastructure.clients;

import java.time.LocalDateTime;
import java.util.List;

public record GithubRepositoryRestResponse(List<Item> items) {

    public record Item(String fullName, String language, LocalDateTime createdAt, LocalDateTime updatedAt, int stargazersCount, int forksCount) {
    }
}
