# Created by azelenov at 9/16/17
Feature: Creating a ticket through API
  # Enter feature description here

  Background:
    Given Zendesk API is accessible

  Scenario: Create ticket basic test
    When I create new ticket