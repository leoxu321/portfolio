Feature: Robustness
  One feature focusing on attempting to break the rules.

  Scenario:
    Given game set up Part2
    When Enter invalid number of players
    And Enter 3 as number of players
    And Enter blank as name for 1st player
    And Enter "Fred" as name for 1st player
    And Enter blank as name for 2nd player
    And Enter "Joe" as name of 2nd player
    And Enter blank as name for 3rd player
    And Enter "Paul" as name for 3rd player
    And Input initial health points as -10
    And Set initial health points of players at 50
    Then Round 1: Distribute 12 cards to each player
    Given Rig Hands Part2

    When Melee 1
    And "Fred" tries to play an "Alchemy" when he has basic weapons and Merlin or Apprentice
    And "Fred" plays "Sorcery 11"
    And "Joe" tries to play a "Swords" card when he has a "Sorcery" card
    And "Joe" tries to play a "Deception" card when he has a "Sorcery" card
    And "Joe" tries to play a "Arrows" card when he has a "Sorcery" card
    And "Joe" tries to play a "Alchemy" card when he has a "Sorcery" card
    And "Joe" plays his "Sorcery 6"
    And "Paul" plays his "Sorcery 7"
    Then "Joe" is the loser with 25 points of injury for this melee, with total injury points 25

    When Melee 2
    And "Joe" starts with "Arrows 8"
    And "Paul" plays "Merlin Any" and tries to assign 16 to it
    And "Paul" assigns 9 to "Merlin".
    And "Fred" plays "Apprentice" and tries to assign 20 to it.
    And "Fred" assigns 10 to "Apprentice".
    Then "Joe" is the loser with 40 points of injury for this melee, with total injury points 65

    When Melee 3
    And "Joe" starts with "Swords 9"
    And "Paul" tries to play an "Alchemy" when he has "Swords 7"
    And "Paul" plays "Swords 7"
    And "Fred" plays "Swords 3"
    Then "Fred" is the loser with 25 points of injury for this melee, with total injury points 25

    When Melee 4
    And "Fred" plays "Deception 9"
    And "Joe" tries to play a "Alchemy" card when he has a "Deception" card
    And "Joe" plays "Deception 6"
    And "Paul" tries to shame but can play "Deception 1"
    And "Paul" plays "Deception 1"
    Then "Paul" is the loser with 25 points of injury for this melee, with total injury points 25