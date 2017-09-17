# Created by azelenov at 9/16/17
Feature: Verifying ticket API endpoints for admin user
  Each ticket are deleting after creating


  Background:
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

