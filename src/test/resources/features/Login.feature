Feature: Swag Labs Login


  Scenario Outline: Login to Swag Labs with different users
    When I login as "<userKey>"
   And I click on the login button
    Examples:
      | userKey                 |
      | standard_user           |
#      | locked_out_user         |
#      | problem_user            |
#      | performance_glitch_user |