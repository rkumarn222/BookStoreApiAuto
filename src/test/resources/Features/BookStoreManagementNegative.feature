Feature: Book Management - Negative Test Scenarios

  @negative @regression
  Scenario: Login with invalid credentials
    Given I send a POST request to "/login" with email "invalid@email.com" and password "wrongpassword"
    Then the response status code should be 401
    And the response should contain an error message

  @negative @regression
  Scenario: Signup with existing email
    Given a unique user email is generated
    And I send a POST request to "/signup" with email "unique_email" and password "password123"
    Then the response status code should be 200
    When I send a POST request to "/signup" with email "unique_email" and password "password456"
    Then the response status code should be 400
    And the response should contain an error message about existing user

  @negative @regression
  Scenario: Create book without authentication
    When I send a POST request to "/books/" with book details name "Test Book", author "Test Author", published year 2023, and summary "A summary" without authentication
    Then the response status code should be 401
    And the response should contain an unauthorized error message

  @negative @regression
  Scenario: Create book with missing required fields
    Given I am an authenticated user for book operations
    When I send a POST request to "/books/" with incomplete book details missing the name field
    Then the response status code should be 400
    And the response should contain validation error message

  @negative @regression
  Scenario: Get book with invalid ID
    Given I am an authenticated user for book operations
    When I send a GET request to "/books/" with invalid book ID "999999"
    Then the response status code should be 404
    And the response should contain "Book not found" message

  @negative @regression
  Scenario: Update book with invalid ID
    Given I am an authenticated user for book operations
    When I send a PUT request to "/books/" with invalid book ID "999999" and updated details name "Updated Book", author "Updated Author", published year 2021, and summary "Updated summary"
    Then the response status code should be 404
    And the response should contain "Book not found" message

  @negative @regression
  Scenario: Delete book with invalid ID
    Given I am an authenticated user for book operations
    When I send a DELETE request to "/books/" with invalid book ID "999999"
    Then the response status code should be 404
    And the response should contain "Book not found" message

  @negative @regression
  Scenario: Access books endpoint without authentication
    When I send a GET request to "/books/" without authentication
    Then the response status code should be 401
    And the response should contain an unauthorized error message

  @negative @regression
  Scenario: Create book with invalid data types
    Given I am an authenticated user for book operations
    When I send a POST request to "/books/" with book details name "Test Book", author "Test Author", published year "invalid_year", and summary "A summary"
    Then the response status code should be 400
    And the response should contain validation error message

  @negative @regression
  Scenario: Update book without authentication
    When I send a PUT request to "/books/" with book ID "1" and updated details name "Updated Book", author "Updated Author", published year 2021, and summary "Updated summary" without authentication
    Then the response status code should be 401
    And the response should contain an unauthorized error message

  @negative @regression
  Scenario: Delete book without authentication
    When I send a DELETE request to "/books/" with book ID "1" without authentication
    Then the response status code should be 401
    And the response should contain an unauthorized error message 