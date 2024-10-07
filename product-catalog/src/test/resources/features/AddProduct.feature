Feature: Add a product to the catalog:
  As a user
  I want to be able to add a new product to the catalog
  So that it can be used

  Scenario: The request is valid
    Given a valid add product request
    When I add the product
    Then I receive status 201
    And the productId is returned
    And the product can be retrieved

  Scenario: The request is invalid
    Given an invalid add product request
    When I add the invalid product
    Then I receive status 400
    And the error message is "Invalid request content."