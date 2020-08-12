package com.org.store.pets.services.stepDefinitions;

import com.org.store.pets.services.common.Client;
import com.org.store.pets.services.common.WireMockClient;
import com.org.store.pets.services.utils.PetStatus;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


import static com.org.store.pets.services.common.Client.getPetStoreServiceStatus;
import static com.org.store.pets.services.common.Constants.WIREMOCK_PETS_STATUS_URL;

public class steps {
    private static final Logger LOGGER = LoggerFactory.getLogger(steps.class);
    PetStatus petStatus;
    HttpResponse<String> response;
    String petName;
    int availabilityCount = 0;
    WireMockClient wireMockClient;

    @Given("PetsStore service response code is {string}")
    public void petsStoreServiceIs(String expectedResponseCode) throws IOException, InterruptedException {
        String actualResponseCode = Integer.toString(getPetStoreServiceStatus());
        Assert.assertTrue("Unexpected Response", actualResponseCode.equalsIgnoreCase(expectedResponseCode));
    }

    @When("the request is made for the status {string}")
    public void theRequestIsMadeForTheStatus(String status) throws IOException, InterruptedException {

        if (status.equalsIgnoreCase("available")) {
            response = Client.getPetsByStatus(PetStatus.AVAILABLE);
        } else if (status.equalsIgnoreCase("pending")) {
            response = Client.getPetsByStatus(PetStatus.PENDING);
        } else {
            response = Client.getPetsByStatus(PetStatus.SOLD);
        }
        LOGGER.info("PetsStoreResponse: {}", response.body());
    }

    @And("fetch the pets status with the name {string}")
    public void fetchThePetsStatusWithTheName(String name) {
        LOGGER.info("Real Service Response: {}", response.body());
        petName = name;
        JSONArray ary = new JSONArray(response.body());
        for (int i = 0; i < ary.length(); ++i) {
            JSONObject obj = ary.getJSONObject(i);
            if (obj.getString("name").equalsIgnoreCase(petName)
                    && obj.getString("status").equalsIgnoreCase(PetStatus.AVAILABLE.getStatus())) {
                availabilityCount++;
            }
        }
    }

    @Then("fetch the pets status from mock with the name {string}")
    public void fetchPetStatusFromMock(String name) {
        LOGGER.info("wiremockResponse: {}", response.body());
        petName = name;
        JSONArray ary = new JSONArray(response.body());
        for (int i = 0; i < ary.length(); ++i) {
            JSONObject obj = ary.getJSONObject(i);
            if (obj.getString("name").equalsIgnoreCase(petName)
                    && obj.getString("status").equalsIgnoreCase(PetStatus.AVAILABLE.getStatus())) {
                availabilityCount++;
            }
        }
    }

    @Then("display number of pets available")
    public void validateNumberOfPetsAvailable() {
        LOGGER.info(String.format("Number of %s Available in Pet store: %s", petName, availabilityCount));
    }


    @Given("PetsStore service is {string}")
    public void petsStoreService(String realServiceStatus) {
        if (realServiceStatus.equalsIgnoreCase("unavailable")) {
            wireMockClient = new WireMockClient();
            wireMockClient.petsStoreStubbing();

        }
    }

    @When("the request is made to mock services")
    public void theRequestIsMadeToMockServices() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(WIREMOCK_PETS_STATUS_URL))
                .build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());

    }
}
