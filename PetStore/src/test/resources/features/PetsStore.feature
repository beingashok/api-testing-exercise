@PetsStoreAPITest
Feature: PetsStore API Validation

  @getPetStatusWhenServiceUp
  Scenario: Validate the PetsStatus
    Given PetsStore service response code is "200"
    When the request is made for the status "available"
    And fetch the pets status with the name "doggie"
    Then display number of pets available

  @getPetStatusWhenServiceUp
  Scenario: Validate the PetsStatus
    Given PetsStore service response code is "200"
    When the request is made for the status "available"
    And fetch the pets status with the name "kobe"
    Then display number of pets available

  @getPetStatusWhenServiceDown
  Scenario: Validate the PetsStatus when the service is down
    Given PetsStore service is "unavailable"
    When the request is made to mock services
    And fetch the pets status from mock with the name "doggie"
    Then display number of pets available