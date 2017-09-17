package org.zendesk.testing;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.basic;
import static io.restassured.RestAssured.given;

/**
 * Created by azelenov on 9/16/17.
 * Step definitions for Gherkin features
 */
public class ZenSteps {
    @Given("^Zendesk API is accessible$")
    public void zendeskAPIIsAccessible() throws Throwable {

        RestAssured.authentication = basic(Helper.getProperty("username") + "/token",
                Helper.getConfig().getProperty("token"));
        RestAssured.baseURI = Helper.getProperty("url");
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .setBaseUri(Helper.getProperty("url"))
                .build();
        RestAssured.defaultParser = Parser.JSON;
        given().log().all().when().get("users/me.json").then().statusCode(200);
    }



    @When("^I create new ticket$")
    public void iCreateNewTicket() throws Throwable {
        given().body(Helper.readFile("ticket.json")).log().all().when().post("tickets.json")
                .then().statusCode(201);
        System.out.println("Thread#" +  Thread.currentThread().getId());

        //TestContext.getCurrent().set("createTicketResp", respBody);
    }
}
