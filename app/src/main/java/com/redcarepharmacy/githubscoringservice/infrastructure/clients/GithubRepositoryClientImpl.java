package com.redcarepharmacy.githubscoringservice.infrastructure.clients;

import com.redcarepharmacy.githubscoringservice.application.spi.GithubRepositoryClient;
import com.redcarepharmacy.githubscoringservice.domain.GithubRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Collections;
import java.util.List;

@Component
public class GithubRepositoryClientImpl implements GithubRepositoryClient {

    private final RestClient restClient;

    public GithubRepositoryClientImpl(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public List<GithubRepository> retrieveGithubRepositories() {
        GithubRepositoryRestResponse restResponse = restClient.get().uri("/search/repositories").retrieve().body(GithubRepositoryRestResponse.class);
        if (restResponse != null) {
            return restResponse.items().stream()
                    .map(item -> new GithubRepository(
                            item.fullName(),
                            item.language(),
                            item.createdAt(),
                            item.updatedAt(),
                            item.stargazersCount(),
                            item.forksCount()))
                    .toList();
        } else {
            return Collections.emptyList();
        }
    }
}
