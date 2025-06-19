package apiController;

import constants.EndPoints;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static io.restassured.RestAssured.given;

public class loginAndSignUpServiceContoller {

    /**
     * Generates a random alphanumeric string of a specified length.
     * @param length The desired length of the random string.
     * @return A randomly generated string.
     */
    public static String generateRandomString(int length) {
        String candidateChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder stringGenerated = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            stringGenerated.append(candidateChars.charAt(random.nextInt(candidateChars.length())));
        }
        return stringGenerated.toString();
    }

    /**
     * Performs a user signup operation.
     * @param email The email for the new user.
     * @param password The password for the new user.
     * @return The API response for the signup request.
     */
    public static Response signUp(String email, String password) {
        Map<String, String> body = new HashMap<>();
        body.put("email", email);
        body.put("password", password);

        return given().contentType(ContentType.JSON)
                .body(body)
                .log().all()
                .when().post(EndPoints.SING_UP)
                .then().log().all().extract().response();
    }

    /**
     * Performs a user login operation.
     * @param email The email of the user.
     * @param password The password of the user.
     * @return The API response for the login request.
     */
    public static Response login(String email, String password) {
        RequestSpecification request = given().contentType(ContentType.JSON).log().all();
        if (email != null && password != null) {
            Map<String, String> body = new HashMap<>();
            body.put("email", email);
            body.put("password", password);
            request.body(body);
        }
        return request.when().post(EndPoints.LOG_IN)
                .then().log().all().extract().response();
    }
}


