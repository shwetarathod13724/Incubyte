Feature: User Registration and Login

  Scenario: Register a new user and log in
    Given I open the Magento home page
    When I register a new account
    Then I should be logged in successfully
    When I log out
    And I log in again with the same credentials
    Then I should be logged in again successfully