package com.redcarepharmacy.githubscoringservice.domain;

import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GithubRepositoryTest {

    @Test
    public void testGetScore_basicScore_computedSuccessfully() {
        // given
        GithubRepository repo = new GithubRepository(
                "test-repo",
                "Java",
                LocalDateTime.of(2020, 1, 1, 0, 0),
                LocalDateTime.of(2021, 1, 1, 0, 0),
                100,
                50
        );
        Clock clock = Clock.fixed(Instant.ofEpochMilli(1749496091689L), ZoneId.systemDefault());

        // when
        double score = repo.getScore(200, 100, clock);

        // then
        assertTrue(score >= 0 && score <= 1, "Score should be between 0 and 1");
        assertEquals(0.6335842011121287, score, "Score should be 0.99 for sample values");

    }

    @Test
    public void testGetScore_oldUpdatedRepository_computedSuccessfully() {
        // given
        GithubRepository repo = new GithubRepository(
                "test-repo",
                "Java",
                LocalDateTime.of(2012, 1, 1, 0, 0),
                LocalDateTime.of(2012, 2, 1, 0, 0),
                100,
                50
        );

        // when
        double score = repo.getScore(200, 100, java.time.Clock.systemUTC());

        // then
        assertTrue(score >= 0 && score <= 1, "Score should be between 0 and 1");
    }

    @Test
    public void testGetScore_maxScore_computedSuccessfully() {
        // given
        Clock clock = Clock.fixed(Instant.ofEpochMilli(1749496091689L), ZoneOffset.UTC);
        LocalDateTime now = LocalDateTime.now(clock);
        GithubRepository repo = new GithubRepository(
                "test-repo",
                "Java",
                LocalDateTime.of(2020, 1, 1, 0, 0),
                now,
                200,
                100
        );

        // when
        double score = repo.getScore(200, 100, clock);

        // then
        assertTrue(score >= 0 && score <= 1, "Score should be between 0 and 1");
        assertTrue(0.99 <= score, "Score should be 0.99 for max score");
    }

}