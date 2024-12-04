package cs3500.hw05.strategy;

import cs3500.hw05.card.ICard;
import cs3500.hw05.card.Direction;
import cs3500.hw05.model.ReadonlyThreeTriosModel;
import cs3500.hw05.model.grid.Posn;
import cs3500.hw05.player.PlayerType;
import java.util.ArrayList;
import java.util.List;

/**
 * The strategy in which the goal is to choose a card that is less likely to be flipped in general.
 * Every card and position is assessed, and a flip risk is calculated for each card.
 */
public class LeastFlippable implements Strategy {

  @Override
  public ICard chooseCard(ReadonlyThreeTriosModel model, PlayerType playerType) {
    if (model == null) {
      throw new IllegalArgumentException("The model cannot be null!");
    }
    List<ICard> hand = model.getCurrentPlayer().getHand();
    List<Posn> availablePositions = getAvailablePositions(model);

    if (availablePositions.isEmpty()) {
      throw new IllegalArgumentException("No available moves: the grid is fully occupied.");
    }

    int minFlipRisk = Integer.MAX_VALUE;
    ICard chosenCard = null;
    Posn bestPosition = null;

    for (ICard card : hand) {
      Posn position = choosePosition(model, playerType, card);
      int flipRisk = calculateFlipRisk(model, playerType, card, position);

      if (flipRisk < minFlipRisk ||
          (flipRisk == minFlipRisk && isPreferredPosition(position, bestPosition)) ||
          (flipRisk == minFlipRisk && position.equals(bestPosition)
              && hand.indexOf(card) < hand.indexOf(chosenCard))) {
        minFlipRisk = flipRisk;
        chosenCard = card;
        bestPosition = position;
      }
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
    int minFlipRisk = Integer.MAX_VALUE;

    for (Posn pos : availablePositions) {
      int flipRisk = calculateFlipRisk(model, playerType, card, pos);

      if (flipRisk < minFlipRisk
          || (flipRisk == minFlipRisk && isPreferredPosition(pos, bestPosition))) {
        minFlipRisk = flipRisk;
        bestPosition = pos;
      }
    }
    if (bestPosition != null) {
      return bestPosition;
    } else {
      return getUpperLeftMostPosition(availablePositions);
    }
  }

  /**
   * Calculates the flip risk for placing a given card at a specified position on the grid. The flip
   * risk is determined by evaluating the potential for adjacent opponent cards to flip the card
   * based on their attack and defense values.
   *
   * @param model      the game model providing access to the grid and card positions
   * @param playerType the type of the player placing the card
   * @param card       the card being placed
   * @param pos        the position on the grid where the card is placed
   * @return the flip risk value, representing the count of adjacent opponent cards with higher
   *     attack values than the defense value of the placed card in the corresponding direction
   */
  private int calculateFlipRisk(ReadonlyThreeTriosModel model, PlayerType playerType, ICard card,
      Posn pos) {
    int risk = 0;
    for (Direction direction : Direction.values()) {
      Posn adjacentPos = getAdjacentPosition(pos, direction);

      // Check if the adjacent position is valid
      if (model.getGrid().isValidPosition(adjacentPos)) {
        ICard adjacentCard = model.getCell(adjacentPos).getCard();

        if (adjacentCard != null && adjacentCard.getOwner() != playerType) {
          int attackValue = adjacentCard.getAttackValue(direction.oppositeDirection());
          int defenseValue = card.getAttackValue(direction);

          if (attackValue > defenseValue) {
            risk++;
          }
        }
      }
    }
    return risk;
  }

  /**
   * Calculates the position adjacent to a specified position in the given direction. Determines the
   * adjacent position based on the grid coordinates, with the new position offset according to the
   * specified direction.
   *
   * @param posn      the original position to find an adjacent position for
   * @param direction the direction in which to find the adjacent position (NORTH, SOUTH, EAST,
   *                  WEST)
   * @return the adjacent position in the specified direction
   * @throws IllegalArgumentException if the provided direction is invalid
   */
  private Posn getAdjacentPosition(Posn posn, Direction direction) {
    int x = posn.getX();
    int y = posn.getY();

    switch (direction) {
      case North:
        return new Posn(x, y - 1);
      case South:
        return new Posn(x, y + 1);
      case East:
        return new Posn(x + 1, y);
      case West:
        return new Posn(x - 1, y);
      default:
        throw new IllegalArgumentException("Invalid direction");
    }
  }


  /**
   * Determines if the specified position is preferred over the current best position. A position is
   * considered preferred if it is closer to the upper-left corner of the grid, favoring rows with
   * smaller Y-coordinates, and in case of a tie, columns with smaller X-coordinates.
   *
   * @param position     the position to evaluate
   * @param bestPosition the current best position to compare against
   * @return {@code true} if the specified position is preferred over the best position,
   * {@code false} otherwise
   */
  private boolean isPreferredPosition(Posn position, Posn bestPosition) {
    return bestPosition == null
        || position.getY() < bestPosition.getY()
        || (position.getY() == bestPosition.getY() && position.getX() < bestPosition.getX());
  }


  /**
   * Finds the upper-leftmost position from a list of available positions. This method iterates
   * through the list to identify the position closest to the top row and leftmost column based on
   * grid coordinates.
   *
   * @param availablePositions the list of available positions to evaluate
   * @return the position that is considered the upper-leftmost according to grid coordinates
   * @throws IllegalArgumentException if the list of available positions is empty
   */
  private Posn getUpperLeftMostPosition(List<Posn> availablePositions) {
    if (availablePositions.isEmpty()) {
      throw new IllegalArgumentException("The list of available positions cannot be empty.");
    }
    Posn upperLeft = availablePositions.get(0);
    for (Posn pos : availablePositions) {
      if (isPreferredPosition(pos, upperLeft)) {
        upperLeft = pos;
      }
    }
    return upperLeft;
  }


  /**
   * Retrieves a list of available positions on the grid where a card can be placed. A position is
   * considered available if it is playable and does not currently contain a card.
   *
   * @param model the readonly model providing access to the grid and cell information
   * @return a list of positions that are available for placing a card
   */
  private List<Posn> getAvailablePositions(ReadonlyThreeTriosModel model) {
    List<Posn> availablePositions = new ArrayList<>();
    int rows = model.getGrid().getRows();
    int cols = model.getGrid().getCols();

    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        Posn pos = new Posn(col, row);
        if (model.getCell(pos).isPlayable() && model.getCell(pos).getCard() == null) {
          System.out.println("an available position is " + pos);
          availablePositions.add(pos);
        }
      }
    }
    return availablePositions;
  }
}

