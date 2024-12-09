package cs3500.hw05.model.battlerule;

import java.util.HashSet;
import java.util.Set;

import cs3500.hw05.card.Direction;
import cs3500.hw05.card.ICard;
import cs3500.hw05.model.grid.IGrid;
import cs3500.hw05.model.grid.Posn;

public class CombinedRule implements BattleRule {

  @Override
  public boolean canFlip(ICard attacker, ICard defender, Direction direction) {
    int attackValue = attacker.getAttackValue(direction);
    int defenseValue = defender.getAttackValue(direction.oppositeDirection());
    if (attackValue == 10 && defenseValue == 1) {
      return true;
    } else if (attackValue == 1 && defenseValue == 10) {
      return false;
    } else {
      return defenseValue > attackValue;
    }

  }

  @Override
  public Set<Posn> applyRule(ICard placedCard, Posn position, IGrid grid) {
    return new HashSet<>();
  }
}
