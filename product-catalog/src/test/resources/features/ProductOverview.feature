Feature: Product Overview:
  As a user
  I want to be able to retrieve a paginated overview of product details
  So that I can place an order with the correct product details

  Scenario: Retrieve the product overview without pagination parameters
    Given existing products
    When I retrieve products without pagination parameters
    Then I receive the expected page of products

  Scenario Template: Retrieve the product overview with pagination parameters
    Given existing products
    When I retrieve page <page> with size <size> of the product overview
    Then I receive the expected page of products
    Examples:
    | page | size |
    | 0    | 10   |
    | 1    | 10   |
    | 0    | 20   |