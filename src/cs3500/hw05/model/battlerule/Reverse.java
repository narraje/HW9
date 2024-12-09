package cs3500.hw05.model.battlerule;

import cs3500.hw05.card.Direction;
import cs3500.hw05.card.ICard;

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
}
