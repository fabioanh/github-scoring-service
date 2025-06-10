package com.redcarepharmacy.githubscoringservice.application.api;

import com.redcarepharmacy.githubscoringservice.domain.GithubRepository;

import java.util.List;

public interface ScoringService {
    void loadGithubRepositories();

    List<GithubRepository> getAllRepositories();

    int getMaxStars();

    int getMaxForks();
}
