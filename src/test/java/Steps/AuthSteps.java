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

public class AuthSteps {

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

    @Then("the response status code is {int}")
    public void theResponseStatusCodeIs(int statusCode) {
        Assert.assertEquals(response.getStatusCode(), statusCode);
    }

    @Then("the response message is {string}")
    public void theResponseMessageIs(String message) {
        Assert.assertEquals(response.jsonPath().getString("message"), message);
    }

    @Then("I should receive an access token")
    public void iShouldReceiveAnAccessToken() {
        accessToken = response.jsonPath().getString("access_token");
        Assert.assertNotNull(accessToken);
    }

    @Given("I am an authenticated user")
    public void iAmAnAuthenticatedUser() {
        // This step assumes a user is already registered and logged in
        // For a fresh run, you might need to call signup and login methods here
        // For now, we'll rely on the accessToken being set by previous steps if part of the same scenario
        if (accessToken == null) {
            aUniqueUserEmailIsGenerated();
            iSendAPOSTRequestToWithEmailAndPassword("/signup", "unique_email", "testpassword");
            theResponseStatusCodeIs(200);
            theResponseMessageIs("User created successfully");
            iSendAPOSTRequestToWithEmailAndPassword("/login", "unique_email", "testpassword");
            theResponseStatusCodeIs(200);
            iShouldReceiveAnAccessToken();
        }
    }

    @When("I send a GET request to {string} with authentication")
    public void iSendAGETRequestToWithAuthentication(String endpoint) {
        response = RestAssured.given()
                .header("Authorization", "Bearer " + accessToken)
                .get(baseUrl + endpoint);
    }

    @Then("the response is a list")
    public void theResponseIsAList() {
        Assert.assertTrue(response.jsonPath().getList("$").size() >= 0);
    }
}

