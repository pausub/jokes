Feature: Jokes REST API
  Users should be able to call api to fetch jokes and relevant data

  Scenario: Fetching a random joke
    When user fetch a random joke
    And user should receive a joke

  Scenario: Fetching a random joke by category
    When user fetch a random joke by given category
    And user should receive a joke with given category

  Scenario: Fetching all categories
    When user fetch all joke categories
    And user should receive at least one category

  Scenario: Searching for jokes
    When user search for jokes with the query "mugger"
    And user should receive at least one joke
    And all jokes should contain "mugger" substring