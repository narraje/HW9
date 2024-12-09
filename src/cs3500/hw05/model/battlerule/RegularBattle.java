package cs3500.hw05.model.battlerule;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cs3500.hw05.card.Direction;
import cs3500.hw05.card.ICard;
import cs3500.hw05.model.grid.IGrid;
import cs3500.hw05.model.grid.Posn;
import cs3500.hw05.model.grid.cell.ICell;
import cs3500.hw05.player.PlayerType;

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

  @Override
  public Set<Posn> applyRule(ICard placedCard, Posn position, IGrid grid) {
    return new HashSet<>();
  }


}
