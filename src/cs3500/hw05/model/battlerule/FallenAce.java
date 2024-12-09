package cs3500.hw05.model.battlerule;

import cs3500.hw05.card.Direction;
import cs3500.hw05.card.ICard;

/**
 * A battle rule in which a card with attack value 1 can flip a card with defense value 10 (A).
 */
public class FallenAce implements BattleRule {

  @Override
  public boolean canFlip(ICard attacker, ICard defender, Direction direction) {
    int attackValue = attacker.getAttackValue(direction);
    int defenseValue = defender.getAttackValue(direction.oppositeDirection());
    if (attacker.getAttackValue(direction) == 1 &&
            defender.getAttackValue(direction.oppositeDirection()) == 10) {
      return true;
    } else if (attackValue == 10 && defenseValue == 1) {
      return false;
    } else {
      return attackValue > defenseValue;
    }

  }
}
