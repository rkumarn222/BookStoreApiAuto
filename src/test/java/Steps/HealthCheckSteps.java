package Steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;

public class HealthCheckSteps {

    private Response response;
    private String baseUrl = "http://localhost:8080"; // Assuming local Jenkins or service

    @Given("the API is running")
    public void theApiIsRunning() {
        // Base URL is set, no specific action needed here
    }

    @When("I send a GET request to the {string} endpoint")
    public void iSendAGETRequestToTheEndpoint(String endpoint) {
        response = RestAssured.get(baseUrl + endpoint);
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int statusCode) {
        Assert.assertEquals(response.getStatusCode(), statusCode);
    }

    @Then("the response body should contain {string} and {string}")
    public void theResponseBodyShouldContainAnd(String key, String value) {
        Assert.assertEquals(response.jsonPath().getString(key), value);
    }
}

