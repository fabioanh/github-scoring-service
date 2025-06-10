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
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
                new GithubRepository("user123/fantastic-tool", "Kotlin", LocalDateTime.of(2020, 1, 1, 0, 0), LocalDateTime.of(2025, 1, 1, 0, 0), 200, 76),
                new GithubRepository("user567/average-tool", "Java", LocalDateTime.of(2020, 1, 1, 0, 0), LocalDateTime.of(2022, 1, 1, 0, 0), 100, 50)
        );
        when(clock.instant()).thenReturn(Instant.parse("2025-06-10T12:30:00Z"));
        when(clock.getZone()).thenReturn(Clock.systemDefaultZone().getZone());
        when(scoringService.getAllRepositories()).thenReturn(sampleRepositories);
        when(scoringService.getMaxStars()).thenReturn(200);
        when(scoringService.getMaxForks()).thenReturn(100);

        // when
        ResultActions getResponse = mockMvc.perform(get("/githubRepositories") //
                .accept(MediaType.APPLICATION_JSON));//

        // then
        verify(scoringService).getAllRepositories();
        getResponse.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{\"name\":\"user123/fantastic-tool\",\"score\":0.9081826957522832,\"language\":\"Kotlin\",\"updatedAt\":\"2025-01-01T00:00:00\",\"stars\":200,\"forks\":76},{\"name\":\"user567/average-tool\",\"score\":0.639521605875012,\"language\":\"Java\",\"updatedAt\":\"2022-01-01T00:00:00\",\"stars\":100,\"forks\":50}]"));
    }

    @Test
    void postLoadGithubRepositories_regularRequest_shouldReturnNoContent() throws Exception {
        // given

        // when
        ResultActions postResponse = mockMvc.perform(post("/githubRepositories/load"));

        // then
        verify(scoringService).loadGithubRepositories();
        postResponse.andExpect(status().isNoContent());

    }
}