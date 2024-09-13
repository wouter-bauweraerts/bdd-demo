Feature: Product Overview
  As a user
  I want to be able to retrieve a paginated overview of product details
  So that I can place an order with the correct product details

  Scenario: Retrieve the product overview without pagination parameters
    Given existing products
    When I retrieve products without pagination parameters
    Then I receive the expected page of products