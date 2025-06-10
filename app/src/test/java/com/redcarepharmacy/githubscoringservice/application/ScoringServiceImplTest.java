package com.redcarepharmacy.githubscoringservice.application;

import com.redcarepharmacy.githubscoringservice.application.spi.GithubRepositoryClient;
import com.redcarepharmacy.githubscoringservice.application.spi.GithubRepositoryRepository;
import com.redcarepharmacy.githubscoringservice.domain.GithubRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScoringServiceImplTest {

    @Mock
    private GithubRepositoryClient githubRepositoryClient;

    @Mock
    private GithubRepositoryRepository githubRepositoryRepository;


    @Test
    void loadGithubRepositories_basicFlow_successfulLoad() {
        // given
        ScoringServiceImpl scoringService = new ScoringServiceImpl(githubRepositoryClient, githubRepositoryRepository);
        List<GithubRepository> retrievedRepositories = List.of(
                new GithubRepository("user123/fantastic-tool", "Kotlin", LocalDateTime.of(2020, 1, 1, 0, 0), LocalDateTime.of(2025, 1, 1, 0, 0), 200, 76),
                new GithubRepository("user567/average-tool", "Java", LocalDateTime.of(2020, 1, 1, 0, 0), LocalDateTime.of(2022, 1, 1, 0, 0), 100, 50)
        );
        when(githubRepositoryClient.retrieveGithubRepositories()).thenReturn(retrievedRepositories);

        // when
        scoringService.loadGithubRepositories(false);

        // then
        verify(githubRepositoryClient).retrieveGithubRepositories();
        verify(githubRepositoryRepository).saveRepositories(eq(retrievedRepositories));
    }

    @Test
    void getAllRepositories_basicFlow_successfulRetrieval() {
        // given
        ScoringServiceImpl scoringService = new ScoringServiceImpl(githubRepositoryClient, githubRepositoryRepository);
        List<GithubRepository> repositoriesFromRepository = List.of(
                new GithubRepository("user123/fantastic-tool", "Kotlin", LocalDateTime.of(2020, 1, 1, 0, 0), LocalDateTime.of(2025, 1, 1, 0, 0), 200, 76),
                new GithubRepository("user567/average-tool", "Java", LocalDateTime.of(2020, 1, 1, 0, 0), LocalDateTime.of(2022, 1, 1, 0, 0), 100, 50)
        );
        when(githubRepositoryRepository.getAllRepositories()).thenReturn(repositoriesFromRepository);

        // when
        List<GithubRepository> allRepositories = scoringService.getAllRepositories();

        // then
        assertFalse(allRepositories.isEmpty());
        assertEquals(repositoriesFromRepository, allRepositories, "Retrieved repositories should match the expected list");
        verify(githubRepositoryRepository).getAllRepositories();
    }
}