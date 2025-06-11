package com.redcarepharmacy.githubscoringservice.infrastructure.clients;

import com.redcarepharmacy.githubscoringservice.application.spi.GithubRepositoryClient;
import com.redcarepharmacy.githubscoringservice.application.spi.GithubRepositoryClientException;
import com.redcarepharmacy.githubscoringservice.domain.GithubRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

@Component
public class GithubRepositoryClientImpl implements GithubRepositoryClient {

    private static final int PAGE_SIZE = 100;

    private static final Logger logger = LoggerFactory.getLogger(GithubRepositoryClientImpl.class);

    private final RestClient restClient;

    public GithubRepositoryClientImpl(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public List<GithubRepository> retrieveGithubRepositories(String query, Instant createdAfter, String language) {
        GithubRepositoryRestResponse restResponse = getGithubRepositoryRestResponse(query);
        if (restResponse != null) {
            return arrangeRepositoryResponse(createdAfter, language, restResponse);
        } else {
            return Collections.emptyList();
        }
    }

    private static List<GithubRepository> arrangeRepositoryResponse(Instant createdAfter, String language, GithubRepositoryRestResponse restResponse) {
        return restResponse.items().stream()
                .filter(item -> createdAfter == null || item.createdAt().isAfter(createdAfter))
                .filter(item -> language == null || language.equalsIgnoreCase(item.language()))
                .map(item -> new GithubRepository(
                        item.fullName(),
                        item.language(),
                        item.createdAt(),
                        item.updatedAt(),
                        item.stargazersCount(),
                        item.forksCount()))
                .toList();
    }

    private GithubRepositoryRestResponse getGithubRepositoryRestResponse(String query) {
        GithubRepositoryRestResponse restResponse = null;
        try {
            restResponse = restClient.get().uri(uriBuilder -> uriBuilder
                    .path("/search/repositories")
                    .queryParam("q", query)
                    .queryParam("per_page", PAGE_SIZE)
                    .build()
            ).retrieve().body(GithubRepositoryRestResponse.class);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new GithubRepositoryClientException();
        }
        return restResponse;
    }
}
