package cs3500.hw05.strategy;

import cs3500.hw05.card.ICard;
import cs3500.hw05.card.Direction;
import cs3500.hw05.model.ReadonlyThreeTriosModel;
import cs3500.hw05.model.grid.Posn;
import cs3500.hw05.model.grid.cell.ICell;
import cs3500.hw05.player.PlayerType;

import java.util.ArrayList;
import java.util.List;

/**
 * The strategy with the goal of flipping as many cards in a turn as possible. A position and card
 * are chosen together.
 */
public class MaxFlips implements Strategy {

  @Override
  public ICard chooseCard(ReadonlyThreeTriosModel model, PlayerType playerType) {
    if (model == null) {
      throw new IllegalArgumentException("The model cannot be null!");
    }
    List<ICard> hand = model.getCurrentPlayer().getHand();
    int maxFlips = -1;
    ICard chosenCard = null;
    Posn bestPosition = null;

    for (ICard card : hand) {
      Posn position = choosePosition(model, playerType, card);
      int flips = countTotalFlips(model, playerType, card, position);

      System.out.println(
          "Card: " + card.getName() + ", Position: " + position + ", Flips: " + flips);

      if (flips > maxFlips
          ||
          (flips == maxFlips && isPreferredPosition(position, bestPosition)) ||
          (flips == maxFlips && position.equals(bestPosition)
              && hand.indexOf(card) < hand.indexOf(chosenCard))) {

        maxFlips = flips;
        chosenCard = card;
        bestPosition = position;
      }
    }

    if (chosenCard == null && !hand.isEmpty()) {
      chosenCard = hand.get(0);
    }
    return chosenCard;
  }

  @Override
  public Posn choosePosition(ReadonlyThreeTriosModel model, PlayerType playerType, ICard card) {
    if (model == null || card == null) {
      throw new IllegalArgumentException("The model or card cannot be null.");
    }
    List<Posn> availablePositions = getAvailablePositions(model);
    Posn bestPosition = null;
    int maxFlips = -1;

    for (Posn pos : availablePositions) {
      int flips = countTotalFlips(model, playerType, card, pos);

      if (flips > maxFlips || (flips == maxFlips && isPreferredPosition(pos, bestPosition))) {
        maxFlips = flips;
        bestPosition = pos;
      }
    }

    if (bestPosition == null && !availablePositions.isEmpty()) {
      bestPosition = getUpperLeftMostPosition(availablePositions);
    }
    return bestPosition;
  }

  private int countTotalFlips(ReadonlyThreeTriosModel model, PlayerType playerType, ICard card,
      Posn posn) {
    int countFlips = 0;

    for (Direction direction : Direction.values()) {
      Posn adjacentPosn = getAdjacentPosition(posn, direction);

      if (isWithinBounds(model, adjacentPosn)) {
        ICell adjacentCell = model.getCell(adjacentPosn);
        if (adjacentCell.isPlayable()) {
          ICard adjacentCard = adjacentCell.getCard();
          if (adjacentCard != null && adjacentCard.getOwner() != playerType) {
            int attackValue = card.getAttackValue(direction);
            int defenseValue = adjacentCard.getAttackValue(direction.oppositeDirection());

            if (attackValue > defenseValue) {
              countFlips += 1 + countTotalFlips(model, playerType, adjacentCard, adjacentPosn);
            }
          }
        }
      }
    }
    System.out.println("The total number of flips at posn " + posn + "is: " + countFlips);
    return countFlips;
  }

  private Posn getAdjacentPosition(Posn posn, Direction direction) {
    int x = posn.getX();
    int y = posn.getY();

    switch (direction) {
      case North:
        return new Posn(x - 1, y);
      case South:
        return new Posn(x + 1, y);
      case East:
        return new Posn(x, y + 1);
      case West:
        return new Posn(x, y - 1);
      default:
        throw new IllegalArgumentException("Invalid direction");
    }
  }

  private boolean isPreferredPosition(Posn position, Posn bestPosition) {
    return bestPosition == null
        || position.getX() < bestPosition.getX()
        || (position.getX() == bestPosition.getX() && position.getY() < bestPosition.getY());
  }

  private Posn getUpperLeftMostPosition(List<Posn> availablePositions) {
    if (availablePositions == null || availablePositions.isEmpty()) {
      throw new IllegalArgumentException("There are no positions to play.");
    }
    Posn upperLeft = availablePositions.get(0);
    for (Posn pos : availablePositions) {
      if (isPreferredPosition(pos, upperLeft)) {
        upperLeft = pos;
      }
    }
    return upperLeft;
  }

  private List<Posn> getAvailablePositions(ReadonlyThreeTriosModel model) {
    List<Posn> availablePositions = new ArrayList<>();
    int rows = model.getGrid().getRows();
    int cols = model.getGrid().getCols();

    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        Posn pos = new Posn(row, col);
        ICell cell = model.getCell(pos);
        if (cell.isPlayable() && cell.getCard() == null) {
          availablePositions.add(pos);
        }
      }
    }
    return availablePositions;
  }

  private boolean isWithinBounds(ReadonlyThreeTriosModel model, Posn posn) {
    int row = posn.getX();
    int col = posn.getY();
    int rows = model.getGrid().getRows();
    int cols = model.getGrid().getCols();
    return row >= 0 && row < rows && col >= 0 && col < cols;
  }
}



