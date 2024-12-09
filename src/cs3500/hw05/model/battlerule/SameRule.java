package cs3500.hw05.model.battlerule;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import cs3500.hw05.card.Direction;
import cs3500.hw05.card.ICard;
import cs3500.hw05.model.grid.IGrid;
import cs3500.hw05.model.grid.Posn;
import cs3500.hw05.model.grid.cell.ICell;
import cs3500.hw05.player.PlayerType;

/**
 * A variant of BattleRule which determines if the attacking value and defending value have the same
 * attack values. Returns true if those values are equal.
 */
public class SameRule implements BattleRule {
  @Override
  public boolean canFlip(ICard attacker, ICard defender, Direction direction) {
    return false;
  }

  @Override
  public Set<Posn> applyRule(ICard placedCard, Posn position, IGrid grid) {
    Set<Posn> flippedPositions = new HashSet<>();
    List<Posn> adjacentPositions = grid.getAdjacentPositions(position);

    for (int i = 0; i < adjacentPositions.size(); i++) {
      Posn pos1 = adjacentPositions.get(i);
      ICell cell1 = grid.getCell(pos1);
      ICard card1 = cell1.getCard();

      if (card1 != null && cell1.isPlayable() && !card1.getOwner().equals(placedCard.getOwner())) {
        for (int j = i + 1; j < adjacentPositions.size(); j++) {
          Posn pos2 = adjacentPositions.get(j);
          ICell cell2 = grid.getCell(pos2);
          ICard card2 = cell2.getCard();

          if (card2 != null && cell2.isPlayable() &&
                  !card2.getOwner().equals(placedCard.getOwner())) {
            // Check for same attack value
            if (card1.getAttackValue(Direction.getDirection(pos1, position)) ==
                    card2.getAttackValue(Direction.getDirection(pos2, position))) {
              flippedPositions.add(pos1);
              flippedPositions.add(pos2);
            }
          }
        }
      }
    }

    for (Posn pos : flippedPositions) {
      ICell cell = grid.getCell(pos);
      if (cell.getCard() != null && !cell.getCard().getOwner().equals(placedCard.getOwner())) {
        cell.flipCardOwner();
      }
    }

    return flippedPositions;
  }
}


