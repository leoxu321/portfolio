Feature: single Melee Scenario
  Check single melee behavior for each category

  Scenario Outline: User scores correctly in a category
    Given game setup adding players
    When First Player Card Played <p1>
    And Second Player Card Played <p2>
    And Third Player Card Played <p3>
    And Fourth Player Card Played <p4>
    Then player is a <loser>
    And They should get <injury> points in that category

    Examples:
      | p1                     | p2              | p3              | p4              | loser | injury |
#     All arrows – none poisoned
      | "Arrows 13"            | "Arrows 5"      | "Arrows 12"     | "Arrows 7"      | "p2"  | 20     |
#      All swords – two poisoned
      | "Swords 6"             | "Swords 7"      | "Swords 15"     | "Swords 13"     | "p1"  | 30     |
#      All sorcery – all poisoned
      | "Sorcery 11"           | "Sorcery 12"    | "Sorcery 6"     | "Sorcery 5"     | "p4"  | 40     |
#      All deception – completes loser positions
      | "Deception 9"          | "Deception 14"  | "Deception 1"   | "Deception 5"   | "p3"  | 25     |
#      Merlin loses
      | "Arrows 13"            | "Arrows 8"      | "Merlin 7"      | "Arrows 14"     | "p3"  | 45     |
#      Merlin is not loser – use Alchemy
      | "Arrows 13"            | "Arrows 8"      | "Merlin 15"     | "Alchemy 15"    | "p2"  | 45     |
#      Apprentice loses
      | "Arrows 13"            | "Arrows 8"      | "Apprentice 7"  | "Arrows 14"     | "p3"  | 25     |
#      Apprentice is not loser
      | "Arrows 13"            | "Arrows 8"      | "Apprentice 15" | "Arrows 14"     | "p2"  | 25     |
#      3-same card feint
      | "Deception 13"         | "Merlin 14"     | "Merlin 14"     | "Merlin 14"     | "p1"  | 80     |
#      Mix of Me and Ap
      | "Deception 8"          | "Merlin 14"     | "Deception 9"   | "Apprentice 10" | "p1"  | 45     |
#      1 feint 2 cards – feint lowest
      | "Swords 10"            | "Swords 1"      | "Swords 2"      | "Merlin 1"      | "p3"  | 40     |
#      1 feint 3 distinct cards
      | "Swords 10"            | "Apprentice 10" | "Swords 15"     | "Merlin 10"     | "p3"  | 40     |
#      1 feint 2 cards – feint non lowest
      | "Swords 10"            | "Swords 1"      | "Swords 2"      | "Merlin 2"      | "p2"  | 40     |
#      Start with alchemy and lose
      | "Alchemy 2"            | "Deception 7"   | "Swords 6"      | "Arrows 8"      | "p1"  | 35     |
#      Start with Al, not lose, using Me and Ap
      | "Alchemy 6"            | "Merlin 7"      | "Apprentice 8"  | "Sorcery 5"     | "p4"  | 45     |
#      Start with alchemy and not lose
      | "Alchemy 12"           | "Deception 7"   | "Swords 6"      | "Arrows 8"      | "p3"  | 35     |
#      Merlin starts and is not loser
      | "Merlin Swords 13"     | "Swords 10"     | "Swords 1"      | "Alchemy 2"     | "p3"  | 40     |
#      Apprentice starts and is not loser
      | "Apprentice Swords 13" | "Swords 10"     | "Swords 1"      | "Swords 2"      | "p3"  | 20     |
#      Feint makes Me loses despite playing first
      | "Merlin Swords 13"     | "Swords 10"     | "Alchemy 10"    | "Apprentice 10" | "p1"  | 40     |
#      Feint makes Ap loses despite playing first
      | "Apprentice Swords 13" | "Swords 10"     | "Alchemy 10"    | "Apprentice 10" | "p1"  | 20     |
#      2 merlins 2nd one in suit
      | "Merlin Deception 13"  | "Deception 7"   | "Merlin 14"     | "Deception 10"  | "p2"  | 70     |
#      2 merlins 2nd one in suit + Ap in suit
      | "Merlin Deception 13"  | "Apprentice 7"  | "Merlin 14"     | "Deception 10"  | "p2"  | 65     |
#      2 feints – no loser
      | "Swords 10"            | "Apprentice 10" | "Swords 11"     | "Merlin 11"     | "-"  | 0     |
#      Same value for all players – no loser
      | "Swords 10"            | "Apprentice 10" | "Alchemy 10"    | "Merlin 10"     | "-"  | 0     |


