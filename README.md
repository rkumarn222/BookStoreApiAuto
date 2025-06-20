# BookStore API Automation Project

This project automates REST API testing for a BookStore application using Java, RestAssured, TestNG, Cucumber, and Allure Reports for reporting. It also includes a basic Python API test suite for demonstration purposes.

---

## Tech Stack

| Component      | Version                       | Purpose                                        |
| -------------- | ----------------------------- | ---------------------------------------------- |
| Java           | 21                            | Core programming language                      |
| Maven          | Latest (compatible with Java 21) | Project build lifecycle & dependency management |
| TestNG         | Latest                        | Test execution and suite configuration         |
| Cucumber       | 7.x                           | BDD (Behavior-Driven Development)              |
| RestAssured    | 5.4.0                         | Simplified API testing using Java              |
| Allure Reports | 2.24.0                        | Generate beautiful test reports                |
| Python         | 3.x                           | Additional API testing with Pytest and Requests |

## Why Allure Reports?

- **API Testing (RestAssured):** Provides detailed insights, including cURL requests and responses.
- **Failures in CI:** Facilitates easy drill-down with screenshots and logs for quick debugging.
- **Large Test Suites:** Offers robust grouping, filtering, and defect mapping capabilities.
- **Test Metrics Reporting:** Enables tracking trends, test duration, and flaky test detection.

## Why TestNG is a Better Fit (for Java Suite):

- **Robust Allure Integration:** Allure integrates more seamlessly with TestNG.
- **Enhanced Control:** TestNG combined with Cucumber provides superior control over test grouping, parallelism, and execution flow.
- **Native Support:** Benefits from TestNG’s native support for dependencies, retries, and configuration.

---

## Prerequisites

### For Java/Maven Tests:
- Java 21 installed and added to your system's PATH.
- Maven installed and added to your system's PATH.
- [Allure Commandline](https://docs.qameta.io/allure/#_installing_a_commandline) (Optional, for local report viewing).

### For Python Tests:
- Python 3.x installed.
- `pip` for package management.
- Install dependencies: `pip install -r api_tests/requirements.txt`

---

## How to Set Up and Run Tests

### Java/Maven/Cucumber Tests:

1.  **Clone the Repository:** If not already done, clone this project from its Git repository.
2.  **API Endpoints Covered:** This automation suite covers the following REST API endpoints:
    *   `POST /signup` – To sign up to the book store.
    *   `POST /login` – To log in after sign up and generate a token.
    *   `POST /books` – Create a new book.
    *   `PUT /books/{id}` – Update an existing book.
    *   `GET /books/{id}` – Fetch a book by ID.
    *   `GET /books` – Fetch all books.
    *   `DELETE /books/{id}` – Delete a book.

3.  **Execute Tests:** Run the automation suite using the `testng.xml` file. This will trigger all feature scenarios written in a human-readable format, ensuring comprehensive test coverage and clear visibility into the executed test cases.

    ```bash
    mvn clean test
    ```

4.  **Generate Allure Reports:** After test execution, Allure reports will be generated. You can find them under `Allure-reports/allure-results`.

    ```bash
    mvn allure:report
    ```

    To view the reports locally, navigate to the `Allure-reports` directory and run:

    ```bash
    allure serve allure-results
    ```

### Python Pytest Tests:

1.  **Navigate to Python Test Directory:**
    ```bash
    cd api_tests
    ```
2.  **Install Dependencies:**
    ```bash
    pip install -r requirements.txt
    ```
3.  **Run Tests:**
    ```bash
    pytest
    ```

---

# CI/CD Integration (Jenkins Example)

This section outlines a basic CI/CD setup using Jenkins for demonstration purposes.

## Prerequisites:

-   Jenkins installed with the following plugins: Git, GitHub, Pipeline, Maven, and Allure plugins (these can be installed via the Jenkins UI).
-   Ngrok (for exposing local Jenkins instance to GitHub webhooks during development/testing).

## Steps for CI/CD Setup (Development/Testing Environment):

1.  **Jenkinsfile in Dev Repo:** A `Jenkinsfile` in your development repository (e.g., `DevRepo`) can be configured to build development code and trigger the QA automation job.

    ```groovy
    pipeline {
      agent any
      stages {
        stage('Build Dev') {
          steps {
            echo 'Build or test dev code here'
          }
        }
        stage('Trigger QA Automation') {
          steps {
            build job: 'QA-Repo'
          }
        }
      }
    }
    ```

2.  **Jenkinsfile in QA Repo:** This project's `Jenkinsfile` (located at the root of this repository) is designed to run the QA automation and generate reports.

    ```groovy
    pipeline {
      agent any
      tools {
        maven 'Maven 3.6.3'
        allure 'Allure'
      }
      stages {
        stage('Checkout') {
          steps {
            git url: '<gitUrl>', branch: '<BranchName>'
          }
        }
        stage('Build and Test') {
          steps {
            sh 'mvn clean test'
          }
        }
        stage('Generate Allure Report') {
          steps {
            sh 'mvn allure:report'
          }
        }
      }
      post {
        always {
          allure([
            includeProperties: false,
            jdk: '',
            results: [[path: 'Allure-reports/allure-results']]
          ])
        }
      }
    }
    ```

3.  **Launch Jenkins:** Start your local Jenkins instance.
4.  **Create Jenkins Jobs:** Create two pipeline jobs in Jenkins: one for your Dev repo and one for your QA repo (this project).
5.  **Configure Jobs:** Configure both jobs with their respective repository URLs and branches. Ensure the QA job is triggered by the Dev job upon successful build.
6.  **GitHub Webhooks (for Dev Repo):** Add a webhook in your Dev repo's GitHub settings to trigger the Dev Jenkins job whenever code is committed.
7.  **Expose Local Jenkins with Ngrok:** Since GitHub cannot directly access your local Jenkins, use Ngrok to create a public URL for your local Jenkins server.

    ```bash
    ngrok http http://localhost:8080
    ```

8.  **Update Webhook Payload URL:** Use the Ngrok-generated URL as the payload URL in your GitHub webhook settings, along with your Jenkins job name (e.g., `https://<ngrok_url>/job/DevRepo/build`).
9.  **Test CI/CD Flow:** Commit any changes to your Dev repo. This should trigger the Dev Jenkins job, and upon success, the QA automation job will run and generate the Allure report.
10. **Automated Builds:** Now, every commit made by developers to the Dev repo will automatically trigger the Dev build and subsequently the QA automation.


#   B o o k S t o r e A P I  
 