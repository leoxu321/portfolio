Feature: Shaming
  Testing Shame
#Scenario E
  Scenario:
    Given Game Set Up Part3
    When Enter invalid number of players
    And Enter 4 as number of players
    And Enter "Fred" as name for 1st player
    And Enter "Joe" as name of 2nd player
    And Enter "Paul" as name for 3rd player
    And Enter "Bob" as name for 4th player
    And Set initial health points of players at 50
    Then Round 1: Distribute 12 cards to each player
    Given Rig Hands E

    When Melee 1
    And "Fred" starts with "Sorcery 3"
    And "Joe" plays his "Alchemy 7"
    And "Paul" tries to shame with no playable "Sorcery" cards
    And "Paul" chooses to discard "Arrows 2"
    And "Bob" plays his "Sorcery 5"
    Then "Fred" is loser with 20 points of injury for this melee

    When Melee 2
    And "Fred" starts with "Swords 5"
    And "Joe" tries to shame with no playable "Swords" cards
    And "Joe" chooses to discard "Deception 5"
    And "Paul" assigns 5 to "Merlin".
    And "Bob" plays his "Swords 3"
    Then "Bob" is loser with 35 points of injury for this melee

    When Melee 3
    And "Bob" starts with "Merlin Any"
    And "Bob" choose suit "Deception" for "Merlin"
    And "Bob" choose value 10 for "Merlin"
    And "Fred" plays his "Alchemy 10"
    And "Joe" tries to shame with no playable "Deception" cards
    And "Joe" chooses to discard "Sorcery 11"
    And "Paul" plays his "Deception 10"
    Then no one is the loser

    When Melee 4
    And "Bob" starts with "Arrows 10"
    And "Fred" plays his "Arrows 4"
    And "Joe" plays his "Arrows 5"
    And "Paul" plays his "Arrows 7"
    Then "Fred" is loser with 25 points of injury for this melee