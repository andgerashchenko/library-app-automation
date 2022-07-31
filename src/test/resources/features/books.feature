Feature: user as a librarian should be able to add a new book
  @library @db @wip
  Scenario: librarian able to add new user entering all required information
    Given user as a "librarian" is on home page
    When user clicks "Books" module
    And user click "+Add Book" button on "Books" page
    And user fills out "Add Book" form
    And user click "Save Changes" button on "Form" page
    Then new book is listed in the database