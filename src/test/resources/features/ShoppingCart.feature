Feature: Swag Labs Login


  Scenario Outline: Login to Swag Labs with different users
    When I login as "<userKey>"
    And I click on the login button
    Then I click on the shopping cart icon

    Examples:
      | userKey                 |
      | standard_user           |
