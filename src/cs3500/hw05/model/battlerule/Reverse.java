package cs3500.hw05.model.battlerule;

import java.util.HashSet;
import java.util.Set;

import cs3500.hw05.card.Direction;
import cs3500.hw05.card.ICard;
import cs3500.hw05.model.grid.IGrid;
import cs3500.hw05.model.grid.Posn;

/**
 * Represents a variant of the BattleRule - if the attacking value has a lower value than the
 * defending value, then the defending card is flipped.
 */
public class Reverse implements BattleRule{



  @Override
  public boolean canFlip(ICard attacker, ICard defender, Direction direction) {
    int attackValue = attacker.getAttackValue(direction);
    int defenseValue = defender.getAttackValue(direction.oppositeDirection());

    return attackValue < defenseValue;
  }

  @Override
  public Set<Posn> applyRule(ICard placedCard, Posn position, IGrid grid) {
    return new HashSet<>();
  }
}
