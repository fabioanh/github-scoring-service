package com.redcarepharmacy.githubscoringservice.application;

import com.redcarepharmacy.githubscoringservice.application.spi.GithubRepositoryClient;
import com.redcarepharmacy.githubscoringservice.domain.GithubRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScoringServiceImplTest {

    @Mock
    private GithubRepositoryClient githubRepositoryClient;

    @Test
    void findGithubRepositories_basicFlow_successfulRetrieval() {
        // given
        ScoringServiceImpl scoringService = new ScoringServiceImpl(githubRepositoryClient);
        List<GithubRepository> retrievedRepositories = List.of(
                new GithubRepository("user123/fantastic-tool", "Java", LocalDateTime.of(2022, 1, 1, 0, 0).toInstant(ZoneOffset.UTC), LocalDateTime.of(2025, 1, 1, 0, 0).toInstant(ZoneOffset.UTC), 200, 76),
                new GithubRepository("user567/average-tool", "Java", LocalDateTime.of(2021, 1, 1, 0, 0).toInstant(ZoneOffset.UTC), LocalDateTime.of(2022, 1, 1, 0, 0).toInstant(ZoneOffset.UTC), 100, 50)
        );
        when(githubRepositoryClient.retrieveGithubRepositories(anyString(), any(Instant.class), anyString())).thenReturn(retrievedRepositories);

        // when
        scoringService.findGithubRepositories("filter keyword", LocalDateTime.of(2020, 1, 1, 0, 0).toInstant(ZoneOffset.UTC), "Java");

        // then
        verify(githubRepositoryClient).retrieveGithubRepositories(anyString(), any(Instant.class), anyString());
    }
}