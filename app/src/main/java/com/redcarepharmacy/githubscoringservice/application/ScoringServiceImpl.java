package com.redcarepharmacy.githubscoringservice.application;

import com.redcarepharmacy.githubscoringservice.application.annotations.DomainService;
import com.redcarepharmacy.githubscoringservice.application.api.ScoringService;
import com.redcarepharmacy.githubscoringservice.application.spi.GithubRepositoryClient;
import com.redcarepharmacy.githubscoringservice.application.spi.GithubRepositoryRepository;
import com.redcarepharmacy.githubscoringservice.domain.GithubRepository;

import java.util.List;

@DomainService
public class ScoringServiceImpl implements ScoringService {

    private final GithubRepositoryClient githubRepositoryClient;
    private final GithubRepositoryRepository githubRepositoryRepository;

    public ScoringServiceImpl(
            GithubRepositoryClient githubRepositoryClient,
            GithubRepositoryRepository githubRepositoryRepository) {
        this.githubRepositoryClient = githubRepositoryClient;
        this.githubRepositoryRepository = githubRepositoryRepository;
    }

    @Override
    public void loadGithubRepositories(Boolean forceUpdate) {
        List<GithubRepository> currentRepositories = this.githubRepositoryRepository.getAllRepositories();
        if (forceUpdate || currentRepositories.isEmpty()) {
            List<GithubRepository> githubRepositories = this.githubRepositoryClient.retrieveGithubRepositories();
            this.githubRepositoryRepository.saveRepositories(githubRepositories);
        }
    }

    @Override
    public List<GithubRepository> getAllRepositories() {
        return this.githubRepositoryRepository.getAllRepositories();
    }
}
