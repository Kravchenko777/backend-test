package lesson3;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class LogMain {

    @BeforeAll
    static void setUp(){
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void getRecipeWithLoggingPositiveTest() {
        given()
                .log()
                .all()
                .when()
                .get("https://api.spoonacular.com/recipes/716429/information")
                 .prettyPeek()
                .then()
                .statusCode(401);
    }
}
