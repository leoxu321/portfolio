Feature: No Winner Testing
  Testing for no winners
#Scenario A
  Scenario:
    Given Game Set Up Part3
    When Enter invalid number of players
    And Enter 3 as number of players
    And Enter "Fred" as name for 1st player
    And Enter "Joe" as name of 2nd player
    And Enter "Paul" as name for 3rd player
    And Set initial health points of players at 25
    Then Round 1: Distribute 12 cards to each player
    Given Rig Hands Round 1A

    When Melee 1
    And "Fred" starts with "Sorcery 3"
    And "Joe" plays his "Alchemy 7"
    And "Paul" plays his "Sorcery 11"
    Then "Fred" is loser with 20 points of injury for this melee

    When Melee 2
    And "Fred" starts with "Swords 5"
    And "Joe" plays his "Alchemy 5"
    And "Paul" assigns 5 to "Merlin".
    Then no one is the loser

    When Melee 3
    And "Fred" starts with "Deception 15"
    And "Joe" plays his "Deception 11"
    And "Paul" plays his "Deception 13"
    Then "Joe" is loser with 15 points of injury for this melee

    When Round 1 is over
    And Round end damage is calculated
    And Round 2: Distribute 12 cards to each player
    Then new leader is assigned in round 2
    Given Rig Hands Round 2A

    When Melee 1
    And "Joe" starts with "Sorcery 2"
    And "Paul" assigns 10 to "Merlin".
    And "Fred" plays his "Sorcery 10"
    Then "Joe" is loser with 35 points of injury for this melee

    When Melee 2
    And "Joe" starts with "Deception 11"
    And "Paul" plays his "Deception 3"
    And "Fred" assigns 10 to "Merlin".
    Then "Paul" is loser with 35 points of injury for this melee

    When Melee 3
    And "Paul" starts with "Deception 13"
    And "Fred" plays his "Deception 3"
    And "Joe" plays his "Alchemy 13"
    Then "Fred" is loser with 15 points of injury for this melee

    When Round 2 is over
    And Round end damage is calculated
    Then game has no winners