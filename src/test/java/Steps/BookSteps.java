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

public class BookSteps {

    private Response response;
    private String baseUrl = "http://localhost:8080";
    private static String accessToken;
    private static String bookId;

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

    @When("I send a POST request to {string} with book details name {string}, author {string}, published year {int}, and summary {string}")
    public void iSendAPOSTRequestToWithBookDetails(String endpoint, String name, String author, int publishedYear, String summary) {
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
        if (response.getStatusCode() == 200) {
            bookId = response.jsonPath().getString("id");
        }
    }

    @Then("the book name should be {string}")
    public void theBookNameShouldBe(String name) {
        Assert.assertEquals(response.jsonPath().getString("name"), name);
    }

    @Then("the book author should be {string}")
    public void theBookAuthorShouldBe(String author) {
        Assert.assertEquals(response.jsonPath().getString("author"), author);
    }

    @Then("the book published year should be {int}")
    public void theBookPublishedYearShouldBe(int year) {
        Assert.assertEquals(response.jsonPath().getInt("published_year"), year);
    }

    @When("I send a GET request to {string}")
    public void iSendAGETRequestTo(String endpoint) {
        response = RestAssured.given()
                .header("Authorization", "Bearer " + accessToken)
                .get(baseUrl + endpoint);
    }

    @Then("the response is a list of books")
    public void theResponseIsAListOfBooks() {
        Assert.assertTrue(response.jsonPath().getList("$").size() >= 0);
    }

    @When("I send a GET request to {string} with the created book ID")
    public void iSendAGETRequestToWithTheCreatedBookID(String endpoint) {
        response = RestAssured.given()
                .header("Authorization", "Bearer " + accessToken)
                .get(baseUrl + endpoint + bookId);
    }

    @When("I send a PUT request to {string} with the created book ID and updated details name {string}, author {string}, published year {int}, and summary {string}")
    public void iSendAPUTRequestToWithTheCreatedBookIDAndUpdatedDetails(String endpoint, String name, String author, int publishedYear, String summary) {
        Map<String, Object> updatedData = new HashMap<>();
        updatedData.put("name", name);
        updatedData.put("author", author);
        updatedData.put("published_year", publishedYear);
        updatedData.put("book_summary", summary);

        response = RestAssured.given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(updatedData)
                .put(baseUrl + endpoint + bookId);
    }

    @When("I send a DELETE request to {string} with the created book ID")
    public void iSendADELETERequestToWithTheCreatedBookID(String endpoint) {
        response = RestAssured.given()
                .header("Authorization", "Bearer " + accessToken)
                .delete(baseUrl + endpoint + bookId);
    }

    @Then("the response message should be {string}")
    public void theResponseMessageShouldBe(String message) {
        Assert.assertEquals(response.jsonPath().getString("message"), message);
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int statusCode) {
        Assert.assertEquals(response.getStatusCode(), statusCode);
    }
}

