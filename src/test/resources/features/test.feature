Feature: Trello Board Automation

  Background: Log in to Trello and navigate to the homepage
    Given the User is on the Trello website
    When the User clicks on the Log in button
    And the User enters email address and clicks on Continue button
    And the User enters password and clicks on Log in button
    Then the User should be redirected to Trello homepage

  Scenario: Create a new board named "VaultN"
    When the User clicks on Create button
    And the User clicks on Create Board button
    And the User enters a new board named "VaultN" in Board title box
    And the User clicks on Create button in Create board popup
    Then the board "VaultN" should be created successfully
    And the User should be redirected to "VaultN" board page
    When the User clicks the Add list button
    And the User adds the following lists to board
      | Backlog |
      | Todo    |
      | Doing   |
      | Testing |
      | Done    |
    Then the lists should be added successfully to the board
    When the User adds the following cards to the list
      | cardName               | listName |
      | Sign up for Trello     | Todo     |
      | Get key and token      | Todo     |
      | Build a collection     | Todo     |
      | Working on Task        | Todo     |
      | UI Automation          | Backlog  |
      | Writing Test Scenarios | Backlog  |
    Then the cards should be added successfully to the respective lists
    When the User moves the following cards:
      | cardName           | fromList | toList  |
      | Sign up for Trello | Todo     | Done    |
      | Get key and token  | Todo     | Testing |
      | Build a collection | Todo     | Doing   |
      | Working on Task    | Todo     | Doing   |
    Then the cards should be moved successfully to their new lists
      | cardName               | listName |
      | Sign up for Trello     | Done     |
      | Get key and token      | Testing     |
      | Build a collection     | Doing     |
      | Working on Task        | Doing     |
    When the User clicks three dots near the Share button
    And the User clicks the Close board button
    And the User clicks the Close button
    Then the board should be closed successfully
    When the User clicks the Permanently delete button
    And the User clicks the delete button
    Then the board "VaultN" should be deleted successfully
