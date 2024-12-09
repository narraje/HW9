package cs3500.hw05.model.battlerule;

import java.util.Map;
import java.util.Set;

import cs3500.hw05.card.Direction;
import cs3500.hw05.card.ICard;
import cs3500.hw05.model.grid.IGrid;
import cs3500.hw05.model.grid.Posn;
import cs3500.hw05.model.grid.cell.ICell;
import cs3500.hw05.player.PlayerType;

/**
 * An interface responsible for determining the rules when cards battle against each other.
 * Holds a canFlip method, determining how one card can be flipped.
 */
public interface BattleRule {

  /**
   * Determines if the attacking card can flip the defending card.
   * @param attacker the attacking ICard
   * @param defender the defending ICard
   * @param direction the direction in which the attack is happening
   * @return if attacker can flip defender
   */
  boolean canFlip(ICard attacker, ICard defender, Direction direction);



  Set<Posn> applyRule(ICard placedCard, Posn position, IGrid grid);
}
