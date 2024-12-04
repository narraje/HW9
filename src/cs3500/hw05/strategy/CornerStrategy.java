package cs3500.hw05.strategy;

import cs3500.hw05.card.Direction;
import cs3500.hw05.card.ICard;
import cs3500.hw05.model.ReadonlyThreeTriosModel;
import cs3500.hw05.model.grid.Posn;
import cs3500.hw05.player.PlayerType;

import java.util.List;

/**
 * Implementation of the strategy where the goal is to place cards in corners, ensuring that only
 * two of their sides are exposed instead of all four. This strategy searches for the best corner to
 * place the card in.
 */
public class CornerStrategy implements Strategy {

  @Override
  public ICard chooseCard(ReadonlyThreeTriosModel model, PlayerType playerType) {
    if (model == null) {
      throw new IllegalArgumentException("The model cannot be null!");
    }
    List<ICard> hand = model.getCurrentPlayer().getHand();
    ICard bestCard = null;
    Posn bestCorner = null;
    int minFlipRisk = Integer.MAX_VALUE;

    List<Posn> corners = getCornerPositions(model);

    for (Posn corner : corners) {
      for (ICard card : hand) {
        int flipRisk = calculateFlipRisk(model, playerType, card, corner);

        // Apply tie-breaking rules
        if (flipRisk < minFlipRisk || (flipRisk == minFlipRisk && isPreferredPosition(corner,
            bestCorner))
            || (flipRisk == minFlipRisk && corner.equals(bestCorner)
            && hand.indexOf(card) < hand.indexOf(bestCard))) {
          minFlipRisk = flipRisk;
          bestCard = card;
          bestCorner = corner;
        }
      }
    }
    if (bestCard != null) {
      return bestCard;
    } else {
      return hand.get(0);
    }
  }

  /**
   * Determines if the specified position is preferred over the current best position based on grid
   * coordinates, favoring rows closer to the top and, in case of a tie, columns closer to the
   * left.
   *
   * @param position     the position to evaluate
   * @param bestPosition the current best position to compare against
   * @return {@code true} if the specified position is preferred over the best position,
   *     {@code false} otherwise.
   */
  private boolean isPreferredPosition(Posn position, Posn bestPosition) {
    return bestPosition == null
        || position.getY() < bestPosition.getY()
        || (position.getY() == bestPosition.getY() && position.getX() < bestPosition.getX());
  }


  /**
   * Calculates the flip risk for a given card placed at a specified position on the grid. The flip
   * risk is determined by evaluating the potential for adjacent opponent cards to flip the card
   * based on their attack and defense values.
   *
   * @param model      the game model providing access to the grid and card positions
   * @param playerType the type of the player placing the card
   * @param card       the card being placed
   * @param corner     the position on the grid where the card is placed
   * @return the flip risk value, representing the count of adjacent opponent cards with higher
   *     attack values than the defense value of the placed card in the corresponding direction
   */
  private int calculateFlipRisk(ReadonlyThreeTriosModel model, PlayerType playerType, ICard card,
      Posn corner) {
    int risk = 0;
    for (Direction direction : Direction.values()) {
      Posn adjacentPos = getAdjacentPosition(corner, direction);

      // Ensure position is valid and within the grid bounds
      if (model.getGrid().isValidPosition(adjacentPos)) {
        ICard adjacentCard = model.getCell(adjacentPos).getCard();

        // Check if the adjacent card belongs to the opponent
        if (adjacentCard != null && adjacentCard.getOwner() != playerType) {
          int attackValue = adjacentCard.getAttackValue(direction.oppositeDirection());
          int defenseValue = card.getAttackValue(direction);

          // Increment risk if opponent's attack is greater than card's defense
          if (attackValue > defenseValue) {
            risk++;
          }
        }
      }
    }
    return risk;
  }


  /**
   * Calculates the position adjacent to the given position in the specified direction.
   *
   * @param posn      the original position
   * @param direction the direction in which to find the adjacent position (NORTH, SOUTH, EAST,
   *                  WEST)
   * @return the adjacent position in the specified direction
   * @throws IllegalArgumentException if an invalid direction is provided
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
   * Retrieves the corner positions of the grid within the given model. Each corner position is
   * represented as a {@link Posn} object, including: top-left, top-right, bottom-left, and
   * bottom-right corners.
   *
   * @param model the readonly model providing access to the grid dimensions
   * @return a list of positions representing the four corners of the grid
   */
  private List<Posn> getCornerPositions(ReadonlyThreeTriosModel model) {
    int rows = model.getGrid().getRows();
    int cols = model.getGrid().getCols();
    return List.of(new Posn(0, 0), new Posn(0, rows - 1), new Posn(cols - 1, 0),
        new Posn(cols - 1, rows - 1));
  }

  @Override
  public Posn choosePosition(ReadonlyThreeTriosModel model, PlayerType playerType, ICard card) {
    if (model == null || card == null) {
      throw new IllegalArgumentException("The model or card cannot be null.");
    }
    List<Posn> corners = getCornerPositions(model);
    Posn bestCorner = null;
    int minFlipRisk = Integer.MAX_VALUE;

    for (Posn corner : corners) {
      int flipRisk = calculateFlipRisk(model, playerType, card, corner);

      if (flipRisk < minFlipRisk || (flipRisk == minFlipRisk && isPreferredPosition(corner,
          bestCorner))) {
        minFlipRisk = flipRisk;
        bestCorner = corner;
      }
    }

    if (bestCorner != null) {
      return bestCorner;
    } else {
      return getUpperLeftMostPosition(corners);
    }
  }

  /**
   * Finds the upper-leftmost position from a list of available positions. This method iterates
   * through the list and identifies the position closest to the top row and leftmost column, based
   * on the grid coordinates.
   *
   * @param availablePositions the list of available positions to evaluate
   * @return the position that is considered upper-leftmost according to grid coordinates
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

}


