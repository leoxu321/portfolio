Feature: Multiple Winner Testing
  Testing for when a game that ends after 1 round and has multiple winners
# need to add starting with a melee merlin
#Scenario C
  Scenario:
    Given Game Set Up Part3
    When Enter 3 as number of players
    And Enter "Fred" as name for 1st player
    And Enter "Joe" as name of 2nd player
    And Enter "Paul" as name for 3rd player
    And Set initial health points of players at 5
    Then Round 1: Distribute 12 cards to each player
    Given Rig Hands C

    When Melee 1
    And "Fred" starts with "Merlin Any"
    And "Fred" choose suit "Sorcery" for "Merlin"
    And "Fred" choose value 4 for "Merlin"
    And "Joe" plays his "Alchemy 7"
    And "Paul" plays his "Sorcery 11"
    Then "Fred" is loser with 40 points of injury for this melee

    When Melee 2
    And "Fred" starts with "Sorcery 3"
    And "Joe" plays "Alchemy 5"
    And "Paul" tries to shame with no playable "Sorcery" cards
    And "Paul" chooses to discard "Swords 5"
    And "Paul" dies from the shame and game ends
    Then "Fred" and "Joe" both win