# API Automation Framework for Bookstore API

## Overview

This project implements an API automation framework for the Bookstore API built with FastAPI. The framework is designed to provide comprehensive test coverage for the API's functionalities, including user authentication and book management operations.

## Technologies Used

- **Python**: Programming language for the test framework.
- **Pytest**: A powerful and flexible testing framework for Python.
- **Requests**: A simple yet elegant HTTP library for Python, used for making API calls.
- **Allure Report**: A flexible lightweight test report tool that creates clear and interactive reports.

## Getting Started

### Prerequisites

- Python 3.7+
- pip
- Allure Commandline (installed via npm or apt)
- FastAPI server running at `http://127.0.0.1:8000`

### Installation

1.  Navigate to the `api_tests` directory:

    ```bash
    cd /home/ubuntu/BookStoreApiAutomatiom/api_tests
    ```

2.  Install the required Python packages:

    ```bash
    pip install -r requirements.txt
    ```

3.  Ensure Allure Commandline is installed. If not, you can install it via npm:

    ```bash
    npm install -g allure-commandline
    ```
    Or via apt (after adding the PPA):
    ```bash
    sudo apt-get update
    sudo apt-get install software-properties-common -y
    sudo apt-add-repository ppa:qameta/allure -y
    sudo apt-get update
    sudo apt-get install allure -y
    ```

### Running the Tests

1.  Ensure the FastAPI server is running. You can start it from the `bookstore/bookstore` directory:

    ```bash
    uvicorn bookstore.bookstore.main:app --reload
    ```

2.  Navigate to the `api_tests` directory:

    ```bash
    cd /home/ubuntu/BookStoreApiAutomatiom/api_tests
    ```

3.  Run the tests and generate Allure results:

    ```bash
    pytest --alluredir=allure-results .
    ```

4.  Generate the Allure report:

    ```bash
    allure generate allure-results --clean -o allure-report
    ```

5.  Open the generated report in your browser:

    ```bash
    allure open allure-report
    ```

## Testing Strategy

### Approach to Writing Test Flows

Our testing strategy focuses on comprehensive API test coverage, ensuring that all critical functionalities of the Bookstore API are validated. We adopted a modular approach, separating tests into logical groups (e.g., `test_health.py`, `test_auth.py`, `test_books.py`) to enhance readability and maintainability.

- **Health Check**: A basic test to ensure the API is up and running.
- **User Authentication**: Tests cover user signup, login, and verification of authenticated access to protected endpoints. Unique email addresses are generated for each signup test to prevent conflicts and ensure test independence.
- **Book Management (CRUD)**: Tests cover the creation, retrieval, update, and deletion of books. Request chaining is implemented where the output (e.g., book ID) of one API call is used as input for subsequent calls.

### Ensuring Reliability and Maintainability

- **Clear and Consistent Naming Conventions**: Test functions and files are named descriptively to indicate their purpose.
- **Parameterized Tests**: Although not extensively used in this initial setup, Pytest's parameterization capabilities can be leveraged for future expansion to test various data inputs efficiently.
- **Fixtures**: Pytest fixtures (`base_url`, `client`, `auth_headers`, `register_and_login_user`) are used to manage test setup and teardown, promoting code reusability and reducing redundancy. For instance, `register_and_login_user` ensures a logged-in user session is available for tests requiring authentication.
- **Assertions**: Robust assertions are used to validate status codes, response payloads, and data integrity, ensuring that API responses meet expected criteria.
- **Environment Configuration**: The `BASE_URL` is defined as a constant, making it easy to switch between different API environments (e.g., dev, QA, prod) by modifying a single line of code.

### Challenges Faced and Overcoming Them

- **Unique User Registration**: Initially, tests for user signup and login failed due to attempts to register users with the same email address across multiple test runs. This was resolved by incorporating `time.time()` into the email generation, ensuring unique email addresses for each test execution.
- **Allure Commandline Installation**: The `allure` command was not initially found. This was resolved by installing `allure-commandline` globally via `npm` and ensuring Java Development Kit (JDK) was installed, as Allure requires Java to run.
- **Data Model Mismatch**: The initial `test_books.py` failed because the book data model in the tests (`title`, `publication_year`) did not match the actual FastAPI application's data model (`name`, `published_year`, `book_summary`). This was corrected by updating the test data and assertions to align with the FastAPI application's `Book` model.

## Sample Test Report

A sample Allure report can be generated by following the "Running the Tests" steps above. The report will be available in the `/home/ubuntu/BookStoreApiAutomatiom/allure-report` directory, providing a detailed overview of test execution, including pass/fail status, test duration, and step-by-step execution details.


