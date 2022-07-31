Feature: user as a librarian should be able to add a new user
  @library
  Scenario: librarian able to add new user entering all required information
    Given user as a "librarian" is on home page
    When user clicks "Users" module
    And user click "+Add User" button on "Users" page
    And user fills out "Add User" form
    And user click "Save Changes" button on "Form" page
    Then new user is visible in the list
