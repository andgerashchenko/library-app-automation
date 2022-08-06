@wip@db
Feature: Books module As a students, I should be able to borrow book

  Scenario: Student borrow new book

    Given user as a "student" is on home page
    And user clicks "Books" module
    And the user searches book name called "Colorless Tsukuru Tazaki"
    When the user clicks Borrow Book
    When user clicks "Borrowing Books" module
    Then verify that book is shown in the page
    And verify logged student has same book in database