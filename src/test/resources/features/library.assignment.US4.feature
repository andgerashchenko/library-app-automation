Feature: As a data consumer, I want UI and DB book information are match.
@db @wip
  Scenario: Verify book information with DB
    Given user as a "librarian" is on home page
    And user clicks "Books" module
    When the user searches for "Colorless Tsukuru Tazaki" book And the user clicks edit book button
    Then book information must match the Database