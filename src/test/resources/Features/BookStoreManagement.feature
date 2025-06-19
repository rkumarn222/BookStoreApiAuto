@BookStoreManagement
Feature: To validate the bookstore by adding , updating, fetching all books and deleteing them from the store with the help of user authentication

  @CRUDOperations @Sanity
  Scenario:To create,update ,fetch book by id and finally delete the book in the book store with signup and login to the book store with a new user
    Given Adding the new book into the store after successful login of user into the system
    When user tries to signup the store with new credentials
    Then validate whether a new user is created with response code 200 and response message User created successfully after signup
    When user tries to login to the bookStore using the  new credentials
    Then validate whether the user has logged in successfully with the response code 200 and response message success

    When user tries to create a new book in the book store using the valid token of the user
    Then verify whether the book is created and the response is success

    When user tries to edit the book with the name
    Then verifies whether the response is 200
    And verify the edited book details values in response for editing name

    When user tries to fetch the book created using the id created for that book
    Then verify whether the response is success

    When user tries to delete the book added in the bookstore using the id
    And verify the response after deleting the book should be success
    When user tries to delete the book added in the bookstore using the id
    And verify the response after deleting the book should be notfound

  @EditBooksScenario @regression
  Scenario: User will validate the api by editing all the field of the book

    Given Adding the new book into the store after successful login of user into the system
    When user tries to signup the store with new credentials
    Then validate whether a new user is created with response code 200 and response message User created successfully after signup
    When user tries to login to the bookStore using the  new credentials
    Then validate whether the user has logged in successfully with the response code 200 and response message success

    When user tries to create a new book in the book store using the valid token of the user
    Then verify whether the book is created and the response is success

    When user tries to edit the book with the name
    Then verifies whether the response is 200
    And verify the edited book details values in response for editing name

    When user tries to fetch the book created using the id created for that book
    Then verify whether the response is success

    When user tries to edit the book with the author
    Then verifies whether the response is 200
    And verify the edited book details values in response for editing author

    When user tries to fetch the book created using the id created for that book
    Then verify whether the response is success

    When user tries to edit the book with the bookSummary
    Then verifies whether the response is 200
    And verify the edited book details values in response for editing bookSummary

    When user tries to fetch the book created using the id created for that book
    Then verify whether the response is success

    When user tries to edit the book with the published_year
    Then verifies whether the response is 200
    And verify the edited book details values in response for editing published_year

    When user tries to fetch the book created using the id created for that book
    Then verify whether the response is success

    @FetchAllBooks @regression
    Scenario: Validate the fetch api by adding multiple books and check whether we are able to fetch the books
      Given Adding the new book into the store after successful login of user into the system
      When user tries to signup the store with new credentials
      Then validate whether a new user is created with response code 200 and response message User created successfully after signup
      When user tries to login to the bookStore using the  new credentials
      Then validate whether the user has logged in successfully with the response code 200 and response message success

      When user tries to create a new book in the book store using the valid token of the user
      Then verify whether the book is created and the response is success

      Given Adding the new book into the store after successful login of user into the system

      When user tries to create a new book in the book store using the valid token of the user
      Then verify whether the book is created and the response is success

      Given Adding the new book into the store after successful login of user into the system

      When user tries to create a new book in the book store using the valid token of the user
      Then verify whether the book is created and the response is success

      Given Adding the new book into the store after successful login of user into the system

      When user tries to create a new book in the book store using the valid token of the user
      Then verify whether the book is created and the response is success

      Given Adding the new book into the store after successful login of user into the system

      When user tries to create a new book in the book store using the valid token of the user
      Then verify whether the book is created and the response is success

      When fetch all the books that added to the book store
      Then verify the details of books that listed

      @FetchBookWithInvalidId @regression
      Scenario: To check whether we are not able to get the book if the bookID is wrong

        Given Adding the new book into the store after successful login of user into the system
        When user tries to signup the store with new credentials
        Then validate whether a new user is created with response code 200 and response message User created successfully after signup
        When user tries to login to the bookStore using the  new credentials
        Then validate whether the user has logged in successfully with the response code 200 and response message success

        When user tries to create a new book in the book store using the valid token of the user
        Then verify whether the book is created and the response is success
        When the user tries to get the book with wrong id
        Then verify whether we do not get a correct response

