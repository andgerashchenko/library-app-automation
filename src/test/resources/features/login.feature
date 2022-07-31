
Feature: User should be able to login to the LibraryCT
  Scenario: Login page accessible
    Given user is on login page
    Then title of the page is "Login - Library"

  @library
  Scenario: User able to log in as a librarian
    Given user is on login page
    When user as a "librarian" enters valid username and password
    And user click "login" button on "login" page
    Then user can see 3 modules on the dashboard page

  @library
  Scenario: User able to log in as a student
    Given user is on login page
    When user as a "student" enters valid username and password
    And user click "login" button on "login" page
    Then user can see 2 modules on the dashboard page
  @library
  Scenario: User unable to log in with invalid credentials
    Given user is on login page
    When user enters invalid username and password
    And user click "login" button on "login" page
    Then user can see error message "Sorry, Wrong Email or Password"



