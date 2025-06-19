package apiController;

import constants.EndPoints;
import data.BookStoreData;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class bookApiServiceController {

    /**
     * Adds a new book to the bookstore.
     * @param bookDetails A map containing the details of the book (e.g., name, author, published_year, book_summary).
     * @param accessToken The access token for authentication.
     * @param bookStoreData An instance of BookStoreData to store response data (though not directly used in this method for storage).
     * @return The API response.
     */
    public static Response addNewBook(Map<String,Object> bookDetails, String accessToken, BookStoreData bookStoreData)
    {
        RequestSpecification request = given().contentType(ContentType.JSON).log().all();

        if (accessToken!=null) {
            request.header("Authorization", accessToken);
        }

        if(!bookDetails.isEmpty())
        {
            request.body(bookDetails);
        }
        return request.when().post(EndPoints.ADD_NEW_BOOK)
                .then().log().all().extract().response();
    }

    /**
     * Edits an existing book in the bookstore.
     * @param bookDetails A map containing the updated details of the book, including 'createdBookId'.
     * @param accessToken The access token for authentication.
     * @return The API response.
     */
    public static Response editTheBook(Map<String,Object> bookDetails, String accessToken)
    {
        RequestSpecification request = given().contentType(ContentType.JSON).log().all();

        if (accessToken!=null) {
            request.header("Authorization", accessToken);
        }

        if(!bookDetails.isEmpty())
        {
            request.body(bookDetails);
        }
        return request.pathParam("book_id",bookDetails.get("createdBookId")).when().put(EndPoints.BY_BOOK_ID)
                .then().log().all().extract().response();
    }

    /**
     * Retrieves details of a book by its ID.
     * @param bookDetails A map containing the 'createdBookId' of the book to retrieve.
     * @param accessToken The access token for authentication.
     * @return The API response.
     */
    public static Response getBookDetailsById(Map<String,Object> bookDetails, String accessToken)
    {
        RequestSpecification request = given().contentType(ContentType.JSON).log().all();

        if (accessToken!=null) {
            request.header("Authorization", accessToken);
        }
        return request.pathParam("book_id",bookDetails.get("createdBookId")).when().get(EndPoints.BY_BOOK_ID)
                .then().log().all().extract().response();
    }

    /**
     * Retrieves all books from the bookstore.
     * @param accessToken The access token for authentication.
     * @return A list containing the API response for fetching all books.
     */
    public static List<Response> getAllBooks(String accessToken)
    {
        List<Response> responses = new ArrayList<>();
        RequestSpecification request = given().contentType(ContentType.JSON).log().all();

        if (accessToken!=null) {
            request.header("Authorization", accessToken);
        }
        Response response= request.when().get(EndPoints.ADD_NEW_BOOK)
                .then().log().all().extract().response();
        return Collections.singletonList(response);
    }


    /**
     * Deletes a book from the bookstore by its ID.
     * @param id The ID of the book to delete.
     * @param accessToken The access token for authentication.
     * @return The API response.
     */
    public static Response deleteTheBookById(String id, String accessToken)
    {
        RequestSpecification request = given().contentType(ContentType.JSON).log().all();

        if (accessToken!=null) {
            request.header("Authorization", accessToken);
        }
        return request.pathParam("book_id",id).when().delete(EndPoints.BY_BOOK_ID)
                .then().log().all().extract().response();
    }

    /**
     * Attempts to retrieve book details with an invalid ID.
     * @param bookDetails A map containing book details (not directly used for the invalid ID, but kept for consistency).
     * @param accessToken The access token for authentication.
     * @return The API response for the invalid ID request.
     */
    public static Response getBookDetailsByInvalidId(Map<String, Object> bookDetails, String accessToken) {
        RequestSpecification request = given().contentType(ContentType.JSON).log().all();

        if (accessToken!=null) {
            request.header("Authorization", accessToken);
        }
        return request.pathParam("book_id","randomid").when().get(EndPoints.BY_BOOK_ID)
                .then().log().all().extract().response();
    }
}


