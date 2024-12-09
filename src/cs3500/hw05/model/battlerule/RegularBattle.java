package cs3500.hw05.model.battlerule;

import cs3500.hw05.card.Direction;
import cs3500.hw05.card.ICard;

/**
 * The regular rules for when two cards battle - if the attacking card has a higher attack value
 * than the defending card, the defending card is flipped.
 */
public class RegularBattle implements BattleRule{
  @Override
  public boolean canFlip(ICard attacker, ICard defender, Direction direction) {
    int attackValue = attacker.getAttackValue(direction);
    int defenseValue = defender.getAttackValue(direction.oppositeDirection());
    return attackValue > defenseValue;
  }
}
