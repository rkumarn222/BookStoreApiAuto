

Feature: Health Check

  @smoke
  Scenario: Verify API health status
    Given the API is running
    When I send a GET request to the "/health" endpoint
    Then the response status code should be 200
    And the response body should contain "status" and "up"


