Feature: Create orders:
  As a user
  I want to be able to create a new order
  So it can be processed in the system

  Scenario Template: I try to create an order with a non-existing product
    Given product <productId> does not exist
    When I try to create an order with product <productId>
    Then I receive status <status>
    And I receive the message <errormessage>
    Examples:
    | productId | status | errormessage |
    | 1234      | 400    | Product 1234 does not exist |