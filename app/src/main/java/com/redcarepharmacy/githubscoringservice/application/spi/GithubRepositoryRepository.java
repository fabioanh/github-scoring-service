package com.redcarepharmacy.githubscoringservice.application.spi;

import com.redcarepharmacy.githubscoringservice.domain.GithubRepository;

import java.util.List;

public interface GithubRepositoryRepository {
    void saveRepositories(List<GithubRepository> githubRepositories);

    List<GithubRepository> getAllRepositories();
}
