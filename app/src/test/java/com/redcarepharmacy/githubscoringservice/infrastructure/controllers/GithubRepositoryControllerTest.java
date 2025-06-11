package com.redcarepharmacy.githubscoringservice.infrastructure.controllers;

import com.redcarepharmacy.githubscoringservice.application.api.ScoringService;
import com.redcarepharmacy.githubscoringservice.domain.GithubRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GithubRepositoryController.class)
class GithubRepositoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ScoringService scoringService;

    @MockitoBean
    private Clock clock;

    @Test
    void getGithubRepositories_regularRequest_shouldReturnOkAndExpectedJsonAndHeaders() throws Exception {
        // given
        List<GithubRepository> sampleRepositories = List.of(
                new GithubRepository("user123/fantastic-tool", "Java", LocalDateTime.of(2020, 1, 1, 0, 0).toInstant(ZoneOffset.UTC), LocalDateTime.of(2025, 1, 1, 0, 0).toInstant(ZoneOffset.UTC), 200, 76),
                new GithubRepository("user567/average-tool", "Java", LocalDateTime.of(2020, 1, 1, 0, 0).toInstant(ZoneOffset.UTC), LocalDateTime.of(2022, 1, 1, 0, 0).toInstant(ZoneOffset.UTC), 100, 100)
        );
        when(clock.instant()).thenReturn(Instant.parse("2025-06-10T12:30:00Z"));
        when(clock.getZone()).thenReturn(Clock.systemDefaultZone().getZone());
        when(scoringService.findGithubRepositories(anyString(), any(Instant.class), anyString())).thenReturn(sampleRepositories);

        // when
        ResultActions getResponse = mockMvc.perform(get("/githubRepositories?query=tool&createdAfter=2019-06-10T12:30:00Z&language=Java")
                .accept(MediaType.APPLICATION_JSON));

        // then
        verify(scoringService).findGithubRepositories(anyString(), any(Instant.class), anyString());
        getResponse.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{\"name\":\"user123/fantastic-tool\",\"score\":0.9173562583356396,\"language\":\"Java\",\"updatedAt\":\"2025-01-01T00:00:00Z\",\"createdAt\":\"2020-01-01T00:00:00Z\",\"stars\":200,\"forks\":76},{\"name\":\"user567/average-tool\",\"score\":0.8126480867424363,\"language\":\"Java\",\"updatedAt\":\"2022-01-01T00:00:00Z\",\"createdAt\":\"2020-01-01T00:00:00Z\",\"stars\":100,\"forks\":100}]"));
    }
}