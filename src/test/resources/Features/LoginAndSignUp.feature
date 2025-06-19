Feature: User Authentication

  @regression
  Scenario: User signup successful
    Given a unique user email is generated
    When I send a POST request to "/signup" with email "unique_email" and password "newpassword"
    Then the response status code is 200
    And the response message is "User created successfully"

  @regression
  Scenario: User login successful
    Given a unique user email is generated
    And I send a POST request to "/signup" with email "unique_email" and password "loginpassword"
    Then the response status code is 200
    And the response message is "User created successfully"
    When I send a POST request to "/login" with email "unique_email" and password "loginpassword"
    Then the response status code is 200
    And I should receive an access token

  @smoke
  Scenario: Authenticated access to books endpoint
    Given I am an authenticated user
    When I send a GET request to "/books/" with authentication
    Then the response status code is 200
    And the response is a list


