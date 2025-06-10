package com.redcarepharmacy.githubscoringservice.application.api;

import com.redcarepharmacy.githubscoringservice.domain.GithubRepository;

import java.util.List;

public interface ScoringService {
    void loadGithubRepositories(Boolean forceUpdate);
    List<GithubRepository> getAllRepositories();
}
