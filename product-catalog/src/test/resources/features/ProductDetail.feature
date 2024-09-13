Feature: Product Detail:
  As a user
  I want to be able to retrieve a single product by ID
  So that I can consult the details

  Scenario: The requested product does not exist
    Given existing products
    When I retrieve a product with a non-existing ID
    Then I receive status 404

  Scenario: The requested product exists
    Given existing products
    When I retrieve a product with an existing ID
    Then I receive the expected product
    And I receive status 200