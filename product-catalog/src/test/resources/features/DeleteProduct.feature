Feature: Delete product:
  As a user
  I want to be able to delete a product
  So that it no longer exists in the system

  Scenario: Delete an existing product
    Given existing products
    When I delete a product
    Then I receive status 204
    And The product no longer exists in the system

  Scenario: Delete a non existing product
    When I delete a non existing product
    Then I receive status 204
    And The product no longer exists in the system