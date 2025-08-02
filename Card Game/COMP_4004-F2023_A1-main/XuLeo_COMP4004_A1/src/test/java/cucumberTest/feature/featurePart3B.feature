Feature: Shame Dead Testing
  Testing for when game ends before 1st round due to shaming
#Scenario B

  Scenario:
    Given Game Set Up Part3
    When Enter 3 as number of players
    And Enter "Fred" as name for 1st player
    And Enter "Joe" as name of 2nd player
    And Enter "Paul" as name for 3rd player
    And Set initial health points of players at 5
    Then Round 1: Distribute 12 cards to each player
    Given Rig Hands B

    When Melee 1
    And "Fred" starts with "Sorcery 3"
    And "Joe" plays his "Alchemy 7"
    And "Paul" plays his "Sorcery 11"
    Then "Fred" is loser with 20 points of injury for this melee

    When Melee 2
    And "Fred" starts with "Sorcery 3"
    And "Joe" plays "Alchemy 5"
    And "Paul" tries to shame with no playable "Sorcery" cards
    And "Paul" chooses to discard "Swords 5"
    Then "Paul" dies from the shame and game ends