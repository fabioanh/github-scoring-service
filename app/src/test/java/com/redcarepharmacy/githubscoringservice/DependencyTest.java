package com.redcarepharmacy.githubscoringservice;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@AnalyzeClasses(packages = "com.redcarepharmacy.githubscoringservice", importOptions = ImportOption.DoNotIncludeTests.class)
public class DependencyTest {

//    @ArchTest
    static final ArchRule layer_dependencies_are_respected = layeredArchitecture().consideringAllDependencies()

            .layer("infrastructure").definedBy("com.redcarepharmacy.githubscoringservice.infrastructure..")
            .layer("application").definedBy("com.redcarepharmacy.githubscoringservice.application..")
            .layer("domain").definedBy("com.redcarepharmacy.githubscoringservice.domain..")

            .whereLayer("infrastructure").mayNotBeAccessedByAnyLayer()
            .whereLayer("application").mayOnlyBeAccessedByLayers("infrastructure")
            .whereLayer("domain").mayOnlyBeAccessedByLayers("application");
}
