package org.zendesk.testing.cucumber;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.path.json.JsonPath;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

/**
 * Created by azelenov on 9/16/17.
 * Steps related to tickets
 */
public class TicketSteps {
    Logger LOGGER = LoggerFactory.getLogger(TicketSteps.class.getName());

    private String ticketUrl;
    private int ticketId;

    @When("^I create new ticket$")
    public void iCreateNewTicket() throws Throwable {
        LOGGER.info("Creating a ticket");
        JsonPath respBody = given().body(Helper.readFile("sample/ticket.json")).when().post("tickets.json")
                .then().statusCode(201).extract().body().jsonPath();
        this.ticketUrl = respBody.getString("ticket.url");
        this.ticketId = respBody.getInt("ticket.id");
    }

    @Then("^I can check my ticket$")
    public void iCanCheckMyTicket() throws Throwable {
        LOGGER.info("Fetching the ticket");
        when().get(ticketUrl).then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schema/ticketSchema.json"))
                .body("ticket.subject",
                        Matchers.equalTo(Helper.extractJsonValue("sample/ticket.json", "ticket.subject")))
                .body("ticket.id", Matchers.equalTo(ticketId));
    }

    @And("^I can delete this ticket$")
    public void iCanDeleteThisTicket() throws Throwable {
        LOGGER.info("Deleting the ticket");
        when().delete(ticketUrl).then().statusCode(204);
    }

    @When("^I add new comment$")
    public void iAddCommentsToIt() throws Throwable {
        LOGGER.info("Adding comment to ticket");
        given().body(Helper.readFile("sample/comment.json")).when().put(ticketUrl).then().statusCode(200);
    }

    @Then("^I see my comment on the ticket$")
    public void iSeeMyCommentOnTheTicket() throws Throwable {
        String url = String.format("%stickets/%s/comments.json", Helper.getZenUrl(), ticketId);
        List<String> comments = when().get(url)
                .then().statusCode(200)
                .extract().body().jsonPath().getList("comments.plain_body");
        boolean check = false;
        for (String comment: comments) {
            if (comment.equals(Helper.extractJsonValue("sample/comment.json", "ticket.comment.body"))) {
                check = true;
            }
        }
        Assert.assertTrue("Checking if comments contain resent one", check);
    }

    @And("^I can see the ticket among others$")
    public void iCanSeeTheTicketAmongOthers() throws Throwable {
        String url = String.format("%stickets", Helper.getZenUrl());
        JsonPath ticketsResp = when().get(url).then().statusCode(200).extract().jsonPath();
        Assert.assertTrue("Checking that tickets show recently created ticket id",
                ticketsResp.getList("tickets.id").contains(253));

    }
}
