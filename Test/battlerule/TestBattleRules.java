package battlerule;

import org.junit.Test;

import cs3500.hw05.card.Direction;
import cs3500.hw05.card.ICard;
import cs3500.hw05.model.battlerule.BattleRule;
import cs3500.hw05.model.battlerule.CompositeRule;
import cs3500.hw05.model.battlerule.FallenAce;
import cs3500.hw05.model.battlerule.RegularBattle;
import cs3500.hw05.model.battlerule.Reverse;
import cs3500.hw05.player.PlayerType;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;



public class TestBattleRules {

  // A simple implementation of ICard for testing purposes
  private static class TestCard implements ICard {
    private final String name;
    private final int[] attackValues; // Order: North, South, East, West
    private PlayerType owner;

    public TestCard(String name, int north, int south, int east, int west, PlayerType owner) {
      this.name = name;
      this.attackValues = new int[]{north, south, east, west};
      this.owner = owner;
    }

    @Override
    public String getName() {
      return name;
    }



    @Override
    public int getAttackValue(Direction direction) {
      return attackValues[direction.ordinal()];
    }

    @Override
    public ICard copy() {
      return new TestCard(name, attackValues[0], attackValues[1], attackValues[2],
              attackValues[3], owner);
    }

    @Override
    public PlayerType getOwner() {
      return owner;
    }

    @Override
    public void setOwner(PlayerType owner) {
      this.owner = owner;
    }

    @Override
    public String getAttackAbbreviation(Direction direction) {
      return String.valueOf(getAttackValue(direction));
    }
  }

  @Test
  public void testNormalRule() {
    BattleRule normalRule = new RegularBattle();

    ICard cardA = new TestCard("Card A", 10, 5, 3, 8, PlayerType.RED);
    ICard cardB = new TestCard("Card B", 6, 12, 7, 4, PlayerType.BLUE);

    assertTrue(normalRule.canFlip(cardA, cardB, Direction.North));
    assertFalse(normalRule.canFlip(cardA, cardB, Direction.South));
  }

  @Test
  public void testReverseRule() {
    BattleRule reverseRule = new Reverse();

    ICard cardA = new TestCard("Card A", 10, 5, 3, 8, PlayerType.RED);
    ICard cardB = new TestCard("Card B", 6, 12, 7, 4, PlayerType.BLUE);

    assertFalse(reverseRule.canFlip(cardA, cardB, Direction.North));
    assertTrue(reverseRule.canFlip(cardA, cardB, Direction.South));
  }

  @Test
  public void testFallenAceRule() {
    BattleRule fallenAceRule = new FallenAce();

    ICard cardA = new TestCard("Card A", 1, 1, 1, 1, PlayerType.RED);
    ICard cardB = new TestCard("Card B", 10, 10, 10, 10, PlayerType.BLUE);


    assertFalse(fallenAceRule.canFlip(cardB, cardA, Direction.North));
    assertTrue(fallenAceRule.canFlip(cardA, cardB, Direction.North));

  }

  @Test
  public void testCompositeRuleWithReverseFirst() {
    CompositeRule compositeRule = new CompositeRule();
    compositeRule.addRule(new Reverse());
    compositeRule.addRule(new FallenAce());

    ICard cardA = new TestCard("Card A", 1, 5, 3, 8, PlayerType.RED);
    ICard cardB = new TestCard("Card B", 14, 12, 7, 4,
            PlayerType.BLUE);

    assertTrue(compositeRule.canFlip(cardB, cardA, Direction.North));
    assertTrue(compositeRule.canFlip(cardA, cardB, Direction.North));
  }

  @Test
  public void testCompositeRuleWithFallenAceFirst() {
    CompositeRule compositeRule = new CompositeRule();
    compositeRule.addRule(new FallenAce());
    compositeRule.addRule(new Reverse());

    ICard cardA = new TestCard("Card A", 1, 5, 3, 8, PlayerType.RED);
    ICard cardB = new TestCard("Card B", 10, 10, 7, 4,
            PlayerType.BLUE);

    assertTrue(compositeRule.canFlip(cardB, cardA, Direction.North));
    assertTrue(compositeRule.canFlip(cardA, cardB, Direction.North));
  }

  @Test
  public void testCompositeRuleAllCombined() {
    CompositeRule compositeRule = new CompositeRule();
    compositeRule.addRule(new Reverse());
    compositeRule.addRule(new FallenAce());
    compositeRule.addRule(new RegularBattle());

    ICard cardA = new TestCard("Card A", 1, 5, 3, 8,
            PlayerType.RED);
    ICard cardB = new TestCard("Card B", 10, 10, 7, 4,
            PlayerType.BLUE);

    assertTrue(compositeRule.canFlip(cardA, cardB, Direction.North));
    assertTrue(compositeRule.canFlip(cardB, cardA, Direction.North));
  }
}
