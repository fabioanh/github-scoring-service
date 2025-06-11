package com.redcarepharmacy.githubscoringservice.application.api;

import com.redcarepharmacy.githubscoringservice.domain.GithubRepository;

import java.time.Instant;
import java.util.List;

public interface ScoringService {
    List<GithubRepository> findGithubRepositories(String query, Instant createdAfter, String language);
}
