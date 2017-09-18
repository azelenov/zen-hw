# Created by azelenov at 9/16/17
@tickets
Feature: Verifying ticket API endpoints for admin user
  Requirements:
  1. Verify you are able to create a ticket
  2. Verify you are able to add a comment to the ticket
  3. Verify you are able to list all tickets for your Zendesk
  4. Verify you are able to delete a ticket

  Background:
    Given I'm admin user
    Given Zendesk API is accessible

  Scenario: Creating ticket
    When I create new ticket
    Then I can check my ticket
    And I can see the ticket among others
    And I can delete this ticket

  Scenario: Adding comment to the ticket
    When I create new ticket
    Then I can check my ticket
    When I add new comment
    Then I see my comment on the ticket
    And I can delete this ticket

