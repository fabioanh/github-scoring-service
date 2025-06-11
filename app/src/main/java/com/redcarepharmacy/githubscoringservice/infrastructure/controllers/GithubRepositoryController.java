package com.redcarepharmacy.githubscoringservice.infrastructure.controllers;

import com.redcarepharmacy.githubscoringservice.application.api.ScoringService;
import com.redcarepharmacy.githubscoringservice.domain.GithubRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Clock;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/githubRepositories")
@Tag(name = "Github Repositories", description = "Exposes the github repositories to allow searching and checking their scores")
public class GithubRepositoryController {

    private final ScoringService scoringService;
    private final Clock clock;

    public GithubRepositoryController(
            ScoringService scoringService,
            Clock clock) {
        this.scoringService = scoringService;
        this.clock = clock;
    }

    @Operation(summary = "Find repositories", description = "Retrieve detailed information about the repositories containing their scores. Here it is possible to filter by date and language.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of repositories found successfully", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = GithubRepositoryResource.class))),
    })
    @GetMapping
    public ResponseEntity<List<GithubRepositoryResource>> getAllRepositories(
            @Parameter(description = "Filter parameter used to retrieve the repositories related to a specific topi", example = "AI")
            @RequestParam String query,
            @Parameter(description = "Filter parameter to retrieve the repositories created after this value", example = "2025-06-10T20:49:00Z")
            @RequestParam(required = false) Instant createdAfter,
            @Parameter(description = "Filter parameter to retrieve the repositories written in this language", example = "TypeScript")
            @RequestParam(required = false) String language) {
        List<GithubRepository> repositories = scoringService.findGithubRepositories(query, createdAfter, language);
        int maxStars = repositories.stream().map(GithubRepository::stars).max(Integer::compareTo).orElse(0);
        int maxForks = repositories.stream().map(GithubRepository::forks).max(Integer::compareTo).orElse(0);
        List<GithubRepositoryResource> response = repositories.stream()
                .map(r -> GithubRepositoryResource.fromDomain(r, maxStars, maxForks, clock))
                .toList();
        return ResponseEntity.ok(response);
    }

}
