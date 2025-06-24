package Steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.http.ContentType;
import org.testng.Assert;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class NegativeTestSteps {

    private Response response;
    private String baseUrl = "http://localhost:8080";
    private static String accessToken;
    private static String uniqueEmail;

    @Given("a unique user email is generated")
    public void aUniqueUserEmailIsGenerated() {
        uniqueEmail = "testuser_" + TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) + "@example.com";
    }

    @When("I send a POST request to {string} with email {string} and password {string}")
    public void iSendAPOSTRequestToWithEmailAndPassword(String endpoint, String email, String password) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("email", email.equals("unique_email") ? uniqueEmail : email);
        requestBody.put("password", password);
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post(baseUrl + endpoint);
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int statusCode) {
        Assert.assertEquals(response.getStatusCode(), statusCode);
    }

    @Then("the response should contain an error message")
    public void theResponseShouldContainAnErrorMessage() {
        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains("error") || responseBody.contains("message"), 
            "Response should contain error message");
    }

    @Then("the response should contain an error message about existing user")
    public void theResponseShouldContainAnErrorMessageAboutExistingUser() {
        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains("already exists") || responseBody.contains("User already exists") || 
            responseBody.contains("email"), "Response should contain existing user error message");
    }

    @When("I send a POST request to {string} with book details name {string}, author {string}, published year {string}, and summary {string} without authentication")
    public void iSendAPOSTRequestToWithBookDetailsWithoutAuthentication(String endpoint, String name, String author, String publishedYear, String summary) {
        Map<String, Object> bookData = new HashMap<>();
        bookData.put("name", name);
        bookData.put("author", author);
        bookData.put("published_year", publishedYear);
        bookData.put("book_summary", summary);

        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(bookData)
                .post(baseUrl + endpoint);
    }

    @Then("the response should contain an unauthorized error message")
    public void theResponseShouldContainAnUnauthorizedErrorMessage() {
        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains("unauthorized") || responseBody.contains("Unauthorized") || 
            responseBody.contains("401"), "Response should contain unauthorized error message");
    }

    @Given("I am an authenticated user for book operations")
    public void iAmAnAuthenticatedUserForBookOperations() {
        if (accessToken == null) {
            String uniqueEmail = "bookuser_" + TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) + "@example.com";
            Map<String, String> signupData = new HashMap<>();
            signupData.put("email", uniqueEmail);
            signupData.put("password", "bookpassword");
            RestAssured.given().contentType(ContentType.JSON).body(signupData).post(baseUrl + "/signup");

            Map<String, String> loginData = new HashMap<>();
            loginData.put("email", uniqueEmail);
            loginData.put("password", "bookpassword");
            Response loginResponse = RestAssured.given().contentType(ContentType.JSON).body(loginData).post(baseUrl + "/login");
            accessToken = loginResponse.jsonPath().getString("access_token");
            Assert.assertNotNull(accessToken);
        }
    }

    @When("I send a POST request to {string} with incomplete book details missing the name field")
    public void iSendAPOSTRequestToWithIncompleteBookDetailsMissingTheNameField(String endpoint) {
        Map<String, Object> bookData = new HashMap<>();
        bookData.put("author", "Test Author");
        bookData.put("published_year", 2023);
        bookData.put("book_summary", "A summary");

        response = RestAssured.given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(bookData)
                .post(baseUrl + endpoint);
    }

    @Then("the response should contain validation error message")
    public void theResponseShouldContainValidationErrorMessage() {
        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains("validation") || responseBody.contains("required") || 
            responseBody.contains("missing") || responseBody.contains("invalid"), 
            "Response should contain validation error message");
    }

    @When("I send a GET request to {string} with invalid book ID {string}")
    public void iSendAGETRequestToWithInvalidBookID(String endpoint, String invalidId) {
        response = RestAssured.given()
                .header("Authorization", "Bearer " + accessToken)
                .get(baseUrl + endpoint + invalidId);
    }

    @Then("the response should contain {string} message")
    public void theResponseShouldContainMessage(String expectedMessage) {
        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains(expectedMessage), 
            "Response should contain: " + expectedMessage);
    }

    @When("I send a PUT request to {string} with invalid book ID {string} and updated details name {string}, author {string}, published year {int}, and summary {string}")
    public void iSendAPUTRequestToWithInvalidBookIDAndUpdatedDetails(String endpoint, String invalidId, String name, String author, int publishedYear, String summary) {
        Map<String, Object> updatedData = new HashMap<>();
        updatedData.put("name", name);
        updatedData.put("author", author);
        updatedData.put("published_year", publishedYear);
        updatedData.put("book_summary", summary);

        response = RestAssured.given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(updatedData)
                .put(baseUrl + endpoint + invalidId);
    }

    @When("I send a DELETE request to {string} with invalid book ID {string}")
    public void iSendADELETERequestToWithInvalidBookID(String endpoint, String invalidId) {
        response = RestAssured.given()
                .header("Authorization", "Bearer " + accessToken)
                .delete(baseUrl + endpoint + invalidId);
    }

    @When("I send a GET request to {string} without authentication")
    public void iSendAGETRequestToWithoutAuthentication(String endpoint) {
        response = RestAssured.given()
                .get(baseUrl + endpoint);
    }

    @When("I send a POST request to {string} with book details name {string}, author {string}, published year {string}, and summary {string}")
    public void iSendAPOSTRequestToWithBookDetailsWithStringYear(String endpoint, String name, String author, String publishedYear, String summary) {
        Map<String, Object> bookData = new HashMap<>();
        bookData.put("name", name);
        bookData.put("author", author);
        bookData.put("published_year", publishedYear);
        bookData.put("book_summary", summary);

        response = RestAssured.given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(bookData)
                .post(baseUrl + endpoint);
    }

    @When("I send a PUT request to {string} with book ID {string} and updated details name {string}, author {string}, published year {int}, and summary {string} without authentication")
    public void iSendAPUTRequestToWithBookIDAndUpdatedDetailsWithoutAuthentication(String endpoint, String bookId, String name, String author, int publishedYear, String summary) {
        Map<String, Object> updatedData = new HashMap<>();
        updatedData.put("name", name);
        updatedData.put("author", author);
        updatedData.put("published_year", publishedYear);
        updatedData.put("book_summary", summary);

        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(updatedData)
                .put(baseUrl + endpoint + bookId);
    }

    @When("I send a DELETE request to {string} with book ID {string} without authentication")
    public void iSendADELETERequestToWithBookIDWithoutAuthentication(String endpoint, String bookId) {
        response = RestAssured.given()
                .delete(baseUrl + endpoint + bookId);
    }
} 