Feature: One Winner Testing
  Shows when a game that ends after 1 round with one winner
  Include a leader starMng a melee a legal alchemy card

#Scenario D
  Scenario:
    Given Game Set Up Part3
    When Enter invalid number of players
    And Enter 3 as number of players
    And Enter "Fred" as name for 1st player
    And Enter "Joe" as name of 2nd player
    And Enter "Paul" as name for 3rd player
    And Set initial health points of players at 20
    Then Round 1: Distribute 12 cards to each player
    Given Rig Hands D

    When Melee 1
    And "Fred" starts with "Sorcery 3"
    And "Joe" plays his "Alchemy 7"
    And "Paul" plays his "Sorcery 11"
    Then "Fred" is loser with 20 points of injury for this melee

    When Melee 2
    And "Fred" starts with "Swords 5"
    And "Joe" plays his "Alchemy 5"
    And "Paul" assigns 10 to "Merlin".
    Then "Paul" is loser with 35 points of injury for this melee

    When Melee 3
    And "Paul" starts with "Alchemy 10"
    And "Joe" plays his "Arrows 10"
    And "Fred" plays his "Deception 4"
    Then "Fred" is loser with 20 points of injury for this melee

    When Round 2
    And Round end damage is calculated
    Then There are losers found with 0 or below health and "Joe" is the winner