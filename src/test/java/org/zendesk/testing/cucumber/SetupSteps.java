package org.zendesk.testing.cucumber;

import cucumber.api.java.en.Given;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;

import java.util.Properties;

import static io.restassured.RestAssured.basic;
import static io.restassured.RestAssured.given;

/**
 * Steps for setting up test preconditions
 */
public class SetupSteps {

    @Given("^Zendesk API is accessible$")
    public void zendeskAPIIsAccessible() throws Throwable {
        Properties props = Helper.getConfig();
        RestAssured.authentication = basic(props.getProperty("username") + "/token",
                props.getProperty("token"));
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .setBaseUri(Helper.getZenUrl())
                .log(LogDetail.ALL)
                .build();
        RestAssured.defaultParser = Parser.JSON;
        given().when().get("users/me.json").then().statusCode(200);
    }

    @Given("^I'm (.*) user$")
    public void setUser(String user) throws Throwable {
        String userConfigFile = user + ".properties";
        Helper.setConfigFile(userConfigFile);
    }

}
