Feature: As a librarian, I want to know borrowed books number

@wip @db
Scenario: verify the total amount of borrowed books
Given user as a "librarian" is on home page
When the librarian gets borrowed books number
Then borrowed books number information must match with DB