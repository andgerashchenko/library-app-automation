Feature: As a data consumer, I want UI and DB book categories are match.
  @db
  Scenario: verify book categories with DB
    Given user as a "librarian" is on home page
    When user clicks "Books" module
    And the user clicks book categories
    Then verify book categories must match book_categories table from db