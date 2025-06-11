package com.redcarepharmacy.githubscoringservice.infrastructure.controllers;

import com.redcarepharmacy.githubscoringservice.application.api.ScoringService;
import com.redcarepharmacy.githubscoringservice.domain.GithubRepository;
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
public class GithubRepositoryController {

    private final ScoringService scoringService;
    private final Clock clock;

    public GithubRepositoryController(
            ScoringService scoringService,
            Clock clock) {
        this.scoringService = scoringService;
        this.clock = clock;
    }

    @GetMapping
    public ResponseEntity<List<GithubRepositoryResource>> getAllRepositories(
            @RequestParam String query,
            @RequestParam(required = false) Instant updatedAfter, //@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @RequestParam(required = false) String language) {
        List<GithubRepository> repositories = scoringService.findGithubRepositories(query, updatedAfter, language);
        int maxStars = repositories.stream().map(GithubRepository::stars).max(Integer::compareTo).orElse(0);
        int maxForks = repositories.stream().map(GithubRepository::forks).max(Integer::compareTo).orElse(0);
        List<GithubRepositoryResource> response = repositories.stream()
                .map(r -> GithubRepositoryResource.fromDomain(r, maxStars, maxForks, clock))
                .toList();
        return ResponseEntity.ok(response);
    }

}
