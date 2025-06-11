package com.redcarepharmacy.githubscoringservice.application;

import com.redcarepharmacy.githubscoringservice.application.annotations.DomainService;
import com.redcarepharmacy.githubscoringservice.application.api.ScoringService;
import com.redcarepharmacy.githubscoringservice.application.spi.GithubRepositoryClient;
import com.redcarepharmacy.githubscoringservice.domain.GithubRepository;

import java.time.Instant;
import java.util.List;

@DomainService
public class ScoringServiceImpl implements ScoringService {

    private final GithubRepositoryClient githubRepositoryClient;

    public ScoringServiceImpl(
            GithubRepositoryClient githubRepositoryClient) {
        this.githubRepositoryClient = githubRepositoryClient;
    }

    @Override
    public List<GithubRepository> findGithubRepositories(String query, Instant updatedAfter, String language) {
        return this.githubRepositoryClient.retrieveGithubRepositories(query, updatedAfter, language);
    }
}
