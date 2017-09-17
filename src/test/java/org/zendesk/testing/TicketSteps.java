package org.zendesk.testing;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static io.restassured.RestAssured.basic;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

/**
 * Created by azelenov on 9/16/17.
 * Step definitions for Gherkin features
 */
public class TicketSteps {
    Logger LOGGER = LoggerFactory.getLogger(TicketSteps.class.getName());

    private String ticketUrl;
    private int ticketId;

    @Given("^Zendesk API is accessible$")
    public void zendeskAPIIsAccessible() throws Throwable {

        RestAssured.authentication = basic(Helper.getProperty("username") + "/token",
                Helper.getConfig().getProperty("token"));
        RestAssured.baseURI = Helper.getProperty("url");
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .setBaseUri(Helper.getProperty("url"))
                .log(LogDetail.ALL)
                .build();
        RestAssured.defaultParser = Parser.JSON;
        given().when().get("users/me.json").then().statusCode(200);
    }



    @When("^I create new ticket$")
    public void iCreateNewTicket() throws Throwable {
        LOGGER.info("Creating a ticket");
        JsonPath respBody = given().body(Helper.readFile("sample_ticket.json")).when().post("tickets.json")
                .then().statusCode(201).extract().body().jsonPath();
        this.ticketUrl = respBody.getString("ticket.url");
        this.ticketId = respBody.getInt("ticket.id");
    }

    @Then("^I can check my ticket$")
    public void iCanCheckMyTicket() throws Throwable {
        LOGGER.info("Fetching the ticket");
        when().get(ticketUrl).then().statusCode(200);
        //TODO: verify new ticket fields
    }

    @And("^I can delete this ticket$")
    public void iCanDeleteThisTicket() throws Throwable {
        LOGGER.info("Deleting the ticket");
        when().delete(ticketUrl).then().statusCode(204);
    }

    @When("^I add new comment$")
    public void iAddCommentsToIt() throws Throwable {
        LOGGER.info("Adding comment to ticket");
        given().body(Helper.readFile("sample_comment.json")).when().put(ticketUrl).then().statusCode(200);
    }

    @Then("^I see my comment on the ticket$")
    public void iSeeMyCommentOnTheTicket() throws Throwable {
        String url = String.format("%stickets/%s/comments.json", Helper.getProperty("url"), ticketId);
        List<String> comments = when().get(url)
                .then().statusCode(200).extract().body().jsonPath().getList("comments.plain_body");
        boolean check = false;
        for (String comment: comments) {
            if (comment.equals(Helper.extractJsonValue("sample_comment.json", "ticket.comment.body"))) {
                check = true;
            }
        }
        Assert.assertTrue("Checking if comments contain resent one", check);
    }

    @And("^I can see the ticket among others$")
    public void iCanSeeTheTicketAmongOthers() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        String url = String.format("%stickets", Helper.getProperty("url"));
        JsonPath ticketsResp = when().get(url).then().statusCode(200).extract().jsonPath();
        //TODO: verify that new ticket is there

    }
}
