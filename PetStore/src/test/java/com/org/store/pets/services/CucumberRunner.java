package com.org.store.pets.services;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/"
        ,glue={"com.org.store.pets.services.stepDefinitions"}
        ,tags = "@PetsStoreAPITest"
)

public class CucumberRunner {

}