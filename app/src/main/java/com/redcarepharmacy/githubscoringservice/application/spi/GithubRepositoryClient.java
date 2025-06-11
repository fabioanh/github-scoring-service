package com.redcarepharmacy.githubscoringservice.application.spi;

import com.redcarepharmacy.githubscoringservice.domain.GithubRepository;

import java.time.Instant;
import java.util.List;

public interface GithubRepositoryClient {
    List<GithubRepository> retrieveGithubRepositories(String query, Instant createdAfter, String language);
}
