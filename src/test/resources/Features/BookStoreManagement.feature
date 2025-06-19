Feature: Book Management

  @smoke @regression
  Scenario: Create a new book
    Given I am an authenticated user for book operations
    When I send a POST request to "/books/" with book details name "Test Book", author "Test Author", published year 2023, and summary "A summary of the test book."
    Then the response status code should be 200
    And the book name should be "Test Book"
    And the book author should be "Test Author"
    And the book published year should be 2023

  @smoke @regression
  Scenario: Get all books
    Given I am an authenticated user for book operations
    When I send a GET request to "/books/"
    Then the response status code should be 200
    And the response is a list of books

  @regression
  Scenario: Get book by ID
    Given I am an authenticated user for book operations
    And I send a POST request to "/books/" with book details name "Book to Get", author "Author to Get", published year 2020, and summary "Summary for book to get."
    Then the response status code should be 200
    When I send a GET request to "/books/" with the created book ID
    Then the response status code should be 200
    And the book name should be "Book to Get"

  @regression
  Scenario: Update an existing book
    Given I am an authenticated user for book operations
    And I send a POST request to "/books/" with book details name "Book to Update", author "Author to Update", published year 2019, and summary "Summary for book to update."
    Then the response status code should be 200
    When I send a PUT request to "/books/" with the created book ID and updated details name "Updated Book", author "Updated Author", published year 2021, and summary "Updated summary."
    Then the response status code should be 200
    And the book name should be "Updated Book"
    And the book author should be "Updated Author"
    And the book published year should be 2021

  @regression
  Scenario: Delete a book
    Given I am an authenticated user for book operations
    And I send a POST request to "/books/" with book details name "Book to Delete", author "Author to Delete", published year 2018, and summary "Summary for book to delete."
    Then the response status code should be 200
    When I send a DELETE request to "/books/" with the created book ID
    Then the response status code should be 200
    And the response message should be "Book deleted successfully"
    When I send a GET request to "/books/" with the created book ID
    Then the response status code should be 404


