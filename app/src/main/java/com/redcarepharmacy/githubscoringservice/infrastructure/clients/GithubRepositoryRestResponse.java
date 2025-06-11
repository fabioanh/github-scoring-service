package com.redcarepharmacy.githubscoringservice.infrastructure.clients;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.List;

public record GithubRepositoryRestResponse(List<Item> items) {

    public record Item(
            @JsonProperty("full_name") String fullName,
            String language,
            @JsonProperty("created_at") Instant createdAt,
            @JsonProperty("updated_at") Instant updatedAt,
            @JsonProperty("stargazers_count") int stargazersCount,
            @JsonProperty("forks_count") int forksCount) {
    }
}
