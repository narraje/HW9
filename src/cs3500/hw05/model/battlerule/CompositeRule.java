package cs3500.hw05.model.battlerule;

import java.util.ArrayList;
import java.util.List;

import cs3500.hw05.card.Direction;
import cs3500.hw05.card.ICard;

/**
 * The composite Battle Rule - when starting the game, rule variants are added here, which are used
 * and can be combined.
 */
public class CompositeRule implements BattleRule{
  private final List<BattleRule> rules;

  public CompositeRule() {
    this.rules = new ArrayList<>();
  }

  /**
   * Adding a rule to our composite Battle Rule.
   * @param rule the BattleRule to be added.
   */
  public void addRule(BattleRule rule) {
    this.rules.add(rule);
  }

  @Override
  public boolean canFlip(ICard attacker, ICard defender, Direction direction) {
    for (BattleRule rule : this.rules) {
      if (rule.canFlip(attacker, defender, direction)) {
        return true;
      }
    }
    return false;
  }

}
