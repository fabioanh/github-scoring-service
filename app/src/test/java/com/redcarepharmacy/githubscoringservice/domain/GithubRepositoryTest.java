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
                LocalDateTime.of(2020, 1, 1, 0, 0).toInstant(ZoneOffset.UTC),
                LocalDateTime.of(2021, 1, 1, 0, 0).toInstant(ZoneOffset.UTC),
                100,
                50
        );
        Clock clock = Clock.fixed(Instant.ofEpochMilli(1749496091689L), ZoneId.systemDefault());

        // when
        double score = repo.getScore(200, 100, clock);

        // then
        assertTrue(score >= 0 && score <= 1, "Score should be between 0 and 1");
        assertEquals(0.639984028281926, score, "Score should be 0.639984028281926 for sample values");

    }

    @Test
    public void testGetScore_oldUpdatedRepository_computedSuccessfully() {
        // given
        GithubRepository repo = new GithubRepository(
                "test-repo",
                "Java",
                LocalDateTime.of(2012, 1, 1, 0, 0).toInstant(ZoneOffset.UTC),
                LocalDateTime.of(2012, 2, 1, 0, 0).toInstant(ZoneOffset.UTC),
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
        Instant now = Instant.now(clock);
        GithubRepository repo = new GithubRepository(
                "test-repo",
                "Java",
                LocalDateTime.of(2020, 1, 1, 0, 0).toInstant(ZoneOffset.UTC),
                now,
                200,
                100
        );

        // when
        double score = repo.getScore(200, 100, clock);

        // then
        assertTrue(score >= 0 && score <= 1, "Score should be between 0 and 1");
        assertEquals(1.0, score, "Score should be 0.99 for max score");
    }

}