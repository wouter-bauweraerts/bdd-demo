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
      | productId | status | errormessage           |
      | 1234      | 409    | Product 1234 not found |

  Scenario: I want to create an order without customer should not be valid
    Given product 1234 exists
    When I try to create an order without customerId for product 1234
    Then I receive status 400
    And The errormessage contains customerId: must not be null

  Scenario: I want to create an order without products
    When I try to create an order without orderlines
    Then I receive status 400
    And The errormessage contains orderlines: must not be empty

  Scenario: I want to create an order without products
    When I try to create an order without products
    Then I receive status 400
    And The errormessage contains productId: must not be null

  Scenario: I want to create an order without quantity
    When I try to create an order without quantity
    Then I receive status 400
    And The errormessage contains quantity: must not be null

  Scenario Outline: I want to create an order with an invalid quantity
    When I try to create an order with quantity <quantity>
    Then I receive status 400
    And The errormessage contains quantity: must be greater than 0
    Examples:
      | quantity |
      | 0        |
      | -1       |