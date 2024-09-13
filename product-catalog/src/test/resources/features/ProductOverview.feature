Feature: Product Overview
  As a user
  I want to be able to retrieve product details
  So that I can place an order with the correct product details

  Scenario: Retrieve paginated product overview
    Given existing products
    When I retrieve products
    Then I receive the first page of products