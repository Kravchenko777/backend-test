package lesson3;

import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;


public class ExampleTest extends AbstractTest {

    @Test
    void getExampleTest() {
        given()
                .when()
                .get(getBaseUrl()+"recipes/716429/information?" +
                        "includeNutrition=false&apiKey=" +getApiKey())
                                .then()
                                .statusCode(200);

        given()
                .when()
                .request(Method.GET,getBaseUrl()+"recipes/716429/information?" +
                        "includeNutrition={Nutrition}&apiKey={apiKey}", false, getApiKey())
                .then()
                .statusCode(200);
    }

    @Test
    void getSpecifyingRequestDataTest() {
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("includeNutrition", "false")
                .pathParam("id", 716429)
                .when()
                .get(getBaseUrl()+"recipes/{id}/information")
                .then()
                .statusCode(200);

        given()
                .when()
                .get(getBaseUrl()+"recipes/{id}/information?" +
                        "includeNutrition={Nutrition}&apiKey={apiKey}",716429, false, getApiKey())
                .then()
                .statusCode(200);

        given()
                .queryParam("apiKey", getApiKey())
                .contentType("application/x-www-form-urlencoded")
                .formParam("title","Pork roast with green beans")
                .when()
                .post(getBaseUrl()+"recipes/cuisine")
                .then()
                .statusCode(200);

        Cookie someCookie = new Cookie
                .Builder("some_cookie", "some_value")
                .setSecured(true)
                .setComment("some comment")
                .build();


        given().cookie("username","max")
                .cookie(someCookie)
                .when()
                .get(getBaseUrl()+"recipes/716429/information?" +
                        "includeNutrition=false&apiKey=" +getApiKey())
                .then()
                .statusCode(200);

        given().headers("username","max")
                .when()
                .get(getBaseUrl()+"recipes/716429/information?" +
                        "includeNutrition=false&apiKey=" +getApiKey())
                .then()
                .statusCode(200);

        given()
                .queryParam("hash", "a3da66460bfb7e62ea1c96cfa0b7a634a346ccbf")
                .queryParam("apiKey", getApiKey())
                .body("{\n"
                        + " \"date\": 1644881179,\n"
                        + " \"slot\": 1,\n"
                        + " \"position\": 0,\n"
                        + " \"type\": \"INGREDIENTS\",\n"
                        + " \"value\": {\n"
                        + " \"ingredients\": [\n"
                        + " {\n"
                        + " \"name\": \"1 banana\"\n"
                        + " }\n"
                        + " ]\n"
                        + " }\n"
                        + "}")
                .when()
                .post(getBaseUrl()+"mealplanner/geekbrains/items")
                .then()
                .statusCode(200);

    }


    @Test
    void getResponseData(){
        Response response = given()
                            .when()
                            .get(getBaseUrl()+"recipes/716429/information?" +
                            "includeNutrition=false&apiKey=" +getApiKey());

        // Get all headers
        Headers allHeaders = response.getHeaders();
        // Get a single header value:
        System.out.println("Content-Encoding: " + response.getHeader("Content-Encoding"));

        // Get all cookies as simple name-value pairs
        Map<String, String> allCookies = response.getCookies();
        // Get a single cookie value:
        System.out.println("CookieName: " + response.getCookie("cookieName"));

        // Get status line
        System.out.println("StatusLine: " + response.getStatusLine());
        // Get status code
        System.out.println("Code: " + response.getStatusCode());


        String cuisine = given()
                .queryParam("apiKey", getApiKey())
                .when()
                .post(getBaseUrl()+"recipes/cuisine")
                .path("cuisine");

        System.out.println("cuisine: " + cuisine);

        response = given()
                .queryParam("apiKey", getApiKey())
                .when()
                .post(getBaseUrl()+"recipes/cuisine")
                .then().extract().response();

        String confidence = given()
                .queryParam("apiKey", getApiKey())
                .when()
                .post(getBaseUrl()+"recipes/cuisine")
                .then().extract()
                .jsonPath()
                .get("confidence")
                .toString();

        System.out.println("confidence: " + confidence);

    }

    @Test
    void getVerifyingResponseData(){

        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("includeNutrition", "false")
                .when()
                .get("https://api.spoonacular.com/recipes/716429/information")
                .body()
                .jsonPath();
        assertThat(response.get("vegetarian"), is(false));
        assertThat(response.get("vegan"), is(false));
        assertThat(response.get("license"), equalTo("CC BY-SA 3.0"));
        assertThat(response.get("pricePerServing"), equalTo(163.15F));
        assertThat(response.get("extendedIngredients[0].aisle"), equalTo("Milk, Eggs, Other Dairy"));


        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("includeNutrition", "false")
                .when()
                .get("https://api.spoonacular.com/recipes/716429/information")
                .then()
                .assertThat()
                 //.cookie("cookieName", "cookieValue")
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .statusLine(containsString("OK"))
                .header("Connection", "keep-alive")
                .header("Content-Length", Integer::parseInt, lessThan(3000))
                .contentType(ContentType.JSON)
                //  .body(equalTo("something"))
                .time(lessThan(2000L));

        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("includeNutrition", "false")
                .response()
                .contentType(ContentType.JSON)
                .time(lessThan(2000L))
                .header("Connection", "keep-alive")
                .expect()
                .body("vegetarian", is(false))
                .body("vegan", is(false))
                .body("license", equalTo("CC BY-SA 3.0"))
                .body("pricePerServing", equalTo(163.15F))
                .body("extendedIngredients[0].aisle", equalTo("Milk, Eggs, Other Dairy"))
                .when()
                .get("https://api.spoonacular.com/recipes/716429/information");

    }

    @Test
    void addMealTest() {
        String id = given()
                .queryParam("hash", "a3da66460bfb7e62ea1c96cfa0b7a634a346ccbf")
                .queryParam("apiKey", getApiKey())
                .body("{\n"
                        + " \"date\": 1644881179,\n"
                        + " \"slot\": 1,\n"
                        + " \"position\": 0,\n"
                        + " \"type\": \"INGREDIENTS\",\n"
                        + " \"value\": {\n"
                        + " \"ingredients\": [\n"
                        + " {\n"
                        + " \"name\": \"1 banana\"\n"
                        + " }\n"
                        + " ]\n"
                        + " }\n"
                        + "}")
                .when()
                .post("https://api.spoonacular.com/mealplanner/geekbrains/items")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("id")
                .toString();

        given()
                .queryParam("hash", "a3da66460bfb7e62ea1c96cfa0b7a634a346ccbf")
                .queryParam("apiKey", getApiKey())
                .delete("https://api.spoonacular.com/mealplanner/geekbrains/items/" + id)
                .then()
                .statusCode(200);
    }



}
