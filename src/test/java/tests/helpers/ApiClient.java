package tests.helpers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import config.Config;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import java.util.Map;

import static io.restassured.RestAssured.given;

//TODO написать javadoc для класса ApiClient
public class ApiClient {

    private static final Logger logger = LoggerFactory.getLogger(ApiClient.class);
    private final ObjectMapper defaultMapper = new ObjectMapper();
    private final ObjectMapper nonNullMapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

    private String convertToJson(Object obj) {
        try {
            return defaultMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
            return  "Unable to serialize request body: " + e.getMessage();
        }
    }

    private String convertToJsonWithoutNulls(Object obj) {
        try {
            return nonNullMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return "Unable to serialize object without nulls: " + e.getMessage();
        }
    }

    private void logRequestDetails(String endpoint, String jsonBody) {
        Allure.step("Request details", () -> {
            Allure.addAttachment("Endpoint", endpoint);
            Allure.addAttachment("Request body", "application/json", jsonBody);
            Allure.addAttachment("CURL", generateCurl(endpoint, jsonBody));
        });
        logger.info("POST to {}", endpoint);
    }

    private void logResponseDetails(Response response) {
        Allure.step("Response details", () -> {
            Allure.addAttachment("Status code", String.valueOf(response.getStatusCode()));
            Allure.addAttachment("Response body", "application/json", response.getBody().asPrettyString());
        });
        logger.info("Response code:  {}", response.getStatusCode());
    }

    private String generateCurl(String endpoint, String jsonBody) {
        return "curl -X POST \"" + RestAssured.baseURI + endpoint + "\" "
                + "-H \"Content-Tupe: application/json\" "
                + "-d '" + jsonBody.replace("'", "\\'") + "'";
    }

    @Step("GET {endpoint}")
    public Response get(String endpoint) {
        return given()
                .baseUri(Config.getBaseUrl())
                .contentType(ContentType.JSON)
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();
    }

    @Step("GET {endpoint}")
    public Response get(String endpoint, Map<String, String> queryParams) {
        return given()
                .baseUri(Config.getBaseUrl())
                .contentType(ContentType.JSON)
                .queryParams(queryParams)
                .log().all()
                .when()
                .get(endpoint)
                .then()
                .log().all()
                .extract()
                .response();
    }

    @Step("POST {endpoint}")
    public Response postWithoutNulls(String endpoint, Object requestBody) {
        String jsonBody = convertToJsonWithoutNulls(requestBody);
        logRequestDetails(endpoint, jsonBody);

        return given()
                .baseUri(Config.getBaseUrl())
                .contentType(ContentType.JSON)
                .header("x-ce2-metaobj-release-id", "AQA_DMON_1")
                .body(jsonBody)
                .log().all()
                .when()
                .post(endpoint)
                .then()
                .log().all()
                .extract()
                .response();
    }

    @Step("POST {endpoint}")
    public Response postWithoutNulls(String endpoint, Object requestBody, Map<String, Object> queryParams) {
        String jsonBody = convertToJsonWithoutNulls(requestBody);
        logRequestDetails(endpoint, jsonBody);

        return given()
                .baseUri(Config.getBaseUrl())
                .contentType(ContentType.JSON)
                .header("x-ce2-metaobj-release-id", "AQA_DMON_1")
                .queryParams(queryParams)
                .body(jsonBody)
                .log().all()
                .when()
                .post(endpoint)
                .then()
                .log().all()
                .extract()
                .response();
    }

    @Step("POST {endpoint}")
    public Response post(String endpoint, Object requestBody) {
        String jsonBody = convertToJson(requestBody);
        logRequestDetails(endpoint, jsonBody);
        System.out.println("ENV: " + Config.getEnv());
        System.out.println("BASE URL: " + Config.getBaseUrl());
        System.out.println("endpoint: " + endpoint);

        return given()
                .baseUri(Config.getBaseUrl())
                .contentType(ContentType.JSON)
                .header("x-ce2-metaobj-release-id", "AQA_DMON_1")
                .body(jsonBody)
                .log().all()
                .when()
                .post(endpoint)
                .then()
                .log().all()
                .extract()
                .response();
    }

    @Step("POST {endpoint}")
    public Response post(String endpoint, Object requestBody, Map<String, String> queryParams) {
        String jsonBody = convertToJson(requestBody);
        logRequestDetails(endpoint, jsonBody);

        return given()
                .baseUri(Config.getBaseUrl())
                .basePath(endpoint)
                .contentType(ContentType.JSON)
                .header("x-ce2-metaobj-release-id", "AQA_DMON_1")
                .queryParams(queryParams)
                .body(jsonBody)
                .log().all()
                .when()
                .post()
                .then()
                .log().all()
                .extract()
                .response();
    }

    @Step("POST {endpoint}")
    public Response post(String baseUrl, String endpoint, Object requestBody) {
        String jsonBody = convertToJson(requestBody);
        logRequestDetails(endpoint, jsonBody);

        return given()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .header("x-ce2-metaobj-release-id", "AQA_DMON_1")
                .body(jsonBody)
                .when()
                .post(endpoint)
                .then()
                .log().all()
                .extract()
                .response();
    }

    @Step("PUT {endpoint}")
    public Response put(String endpoint, Object body) {
        return given()
                .baseUri(Config.getBaseUrl())
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .put(endpoint)
                .then()
                .extract()
                .response();
    }

    @Step("DELETE {endpoint}")
    public Response delete(String endpoint, Object body) {
        return given()
                .baseUri(Config.getBaseUrl())
                .contentType(ContentType.JSON)
                .when()
                .delete(endpoint)
                .then()
                .extract()
                .response();
    }


}
