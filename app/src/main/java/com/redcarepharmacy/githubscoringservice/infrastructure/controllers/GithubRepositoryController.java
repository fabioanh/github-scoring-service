package com.redcarepharmacy.githubscoringservice.infrastructure.controllers;

import com.redcarepharmacy.githubscoringservice.application.api.ScoringService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Clock;
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
    public ResponseEntity<List<GithubRepositoryResource>> getAllRepositories() {
        int maxStars = scoringService.getMaxStars();
        int maxForks = scoringService.getMaxForks();
        List<GithubRepositoryResource> response = scoringService.getAllRepositories()
                .stream()
                .map(r -> GithubRepositoryResource.fromDomain(r, maxStars, maxForks, clock))
                .toList();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/load")
    public ResponseEntity loadGithubRepositories() {
        scoringService.loadGithubRepositories();
        return ResponseEntity.noContent().build();
    }
}
