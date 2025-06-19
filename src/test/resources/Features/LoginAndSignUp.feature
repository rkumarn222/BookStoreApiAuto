@LoginAndSignUp
Feature: To validate the login flow and Signup flow for the user for book store management


  @NewSignUp @Sanity
  Scenario:To Signup to the bookstore by creating account with new email and password
    Given Signup book store with new email and password
    When user tries to signup the store with new credentials
    Then validate whether a new user is created with response code 200 and response message User created successfully after signup

  @SignUpWithAlreadyExistingID @regression
  Scenario:To Signup to the bookstore by creating account with new email and password
    Given Signup book store with new email and password
    When user tries to signup the store with old credentials
    Then validate whether a new user is created with response code 400 and response message Email already registered after signup

  @SignUpWithOnlyPassword @regression
  Scenario:To Signup to the bookstore by creating account with new email and password
    Given Signup book store with new email and password
    When user tries to signup the store with password credentials
    Then validate whether a new user is created with response code 400 and response message Email already registered after signup

  @SignUpWithOnlyEmailID @regression
  Scenario:To Signup to the bookstore by creating account with new email and password
    Given Signup book store with new email and password
    When user tries to signup the store with email credentials
    Then validate whether a new user is created with response code 200 and response message User created successfully after signup

  @SignUpAndLoginUser @sanity
  Scenario:To Signup to the bookstore by creating account with new email and password and login to that user
    Given Signup book store with new email and password
    When user tries to signup the store with new credentials
    Then validate whether a new user is created with response code 200 and response message User created successfully after signup
    When user tries to login to the bookStore using the  new credentials
    Then validate whether the user has logged in successfully with the response code 200 and response message success


  @LoginBeforeSignUp @regression
  Scenario: Verify by logging with the credentials which is not yet signed into bookstore system
    When user tries to login to the bookStore using the  noSignUpUser
    Then validate whether the user has logged in successfully with the response code 400 and response message incorrectCredentials

  @LoginAPIValidationWithMissingParam @regression
  Scenario: Verify by logging with the credentials which is not yet signed into bookstore system
    When user tries to login to the bookStore using the  missingParam
    Then validate whether the user has logged in successfully with the response code 422 and response message missingParam
