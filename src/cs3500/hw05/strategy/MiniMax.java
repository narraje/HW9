package cs3500.hw05.strategy;

import cs3500.hw05.card.ICard;
import cs3500.hw05.card.Direction;
import cs3500.hw05.model.ReadonlyThreeTriosModel;
import cs3500.hw05.model.grid.Posn;
import cs3500.hw05.player.IPlayer;
import cs3500.hw05.player.PlayerType;
import java.util.ArrayList;
import java.util.List;

/**
 * Strategy in which the approach is to minimize the maximum number of flips that the opponent can
 * achieve. Essentially, trying to leave the opponent in a situation in which they have no good
 * moves.
 */
public class MiniMax implements Strategy {

  @Override
  public ICard chooseCard(ReadonlyThreeTriosModel model, PlayerType playerType) {
    if (model == null) {
      throw new IllegalArgumentException("The model cannot be null!");
    }
    List<ICard> hand = model.getCurrentPlayer().getHand();
    int minOppFlips = Integer.MAX_VALUE;
    ICard chosenCard = null;
    Posn bestPosition = null;

    for (ICard card : hand) {
      Posn position = choosePosition(model, playerType, card);
      int opponentMaxFlips = calculateOpponentMaxFlips(model, playerType, card, position);

      if (opponentMaxFlips < minOppFlips ||
          (opponentMaxFlips == minOppFlips && isPreferredPosition(position, bestPosition)) ||
          (opponentMaxFlips == minOppFlips && position.equals(bestPosition)
              && hand.indexOf(card) < hand.indexOf(chosenCard))) {

        minOppFlips = opponentMaxFlips;
        chosenCard = card;
        bestPosition = position;
      }
    }

    if (chosenCard != null) {
      return chosenCard;
    } else if (!hand.isEmpty()) {
      return hand.get(0);
    } else {
      throw new IllegalStateException("No cards available to choose.");
    }
  }

  @Override
  public Posn choosePosition(ReadonlyThreeTriosModel model, PlayerType playerType, ICard card) {
    if (model == null || card == null) {
      throw new IllegalArgumentException("The model or card cannot be null.");
    }

    List<Posn> availablePositions = getAvailablePositions(model);
    Posn bestPosition = null;
    int minOppFlips = Integer.MAX_VALUE;

    for (Posn pos : availablePositions) {
      int opponentMaxFlips = calculateOpponentMaxFlips(model, playerType, card, pos);

      if (opponentMaxFlips < minOppFlips ||
          (opponentMaxFlips == minOppFlips && isPreferredPosition(pos, bestPosition))) {

        minOppFlips = opponentMaxFlips;
        bestPosition = pos;
      }
    }

    return bestPosition != null ? bestPosition : getUpperLeftMostPosition(availablePositions);
  }

  /**
   * Calculates the maximum number of flips the opponent could achieve with any card in their hand
   * at any available position on the grid. This method evaluates all possible moves the opponent
   * can make and returns the highest flip count achievable by the opponent in a single move.
   *
   * @param model          the readonly model providing access to the game grid, cells, and player
   *                       hands
   * @param playerType     the type of the current player (used to determine the opponent)
   * @param playerCard     the card being evaluated for placement by the current player
   * @param playerPosition the position on the grid where the current player is considering placing
   *                       their card
   * @return the maximum number of flips the opponent could achieve in a single move
   */
  private int calculateOpponentMaxFlips(ReadonlyThreeTriosModel model, PlayerType playerType,
      ICard playerCard, Posn playerPosition) {
    int maxFlips = 0;
    PlayerType opponentType = playerType == PlayerType.RED ? PlayerType.BLUE : PlayerType.RED;

    IPlayer opponentPlayer = model.getPlayer1().getPlayerType() == opponentType
        ? model.getPlayer1() : model.getPlayer2();

    List<ICard> opponentHand = model.getPlayerHand(opponentPlayer);

    for (ICard opponentCard : opponentHand) {
      for (Posn pos : getAvailablePositions(model)) {
        int flips = countTotalFlips(model, opponentType, opponentCard, pos);
        if (flips > maxFlips) {
          maxFlips = flips;
        }
      }
    }
    return maxFlips;
  }

  /**
   * Counts the total number of flips that would occur if the specified card were placed at the
   * specified position on the grid. A flip occurs when the attack value of the card in a particular
   * direction is greater than the defense value of an opponent's adjacent card in the opposite
   * direction.
   *
   * @param model      the readonly model providing access to the game grid and cell information
   * @param playerType the type of the player making the move
   * @param card       the card being evaluated for placement
   * @param posn       the position on the grid where the card is being considered for placement
   * @return the total number of flips that would result from placing the card at the specified
   *     position
   */
  private int countTotalFlips(ReadonlyThreeTriosModel model, PlayerType playerType,
      ICard card, Posn posn) {
    int countFlips = 0;

    for (Direction direction : Direction.values()) {
      Posn adjacentPosn = getAdjacentPosition(posn, direction);
      if (model.getGrid().isValidPosition(adjacentPosn)) {
        ICard adjacentCard = model.getCell(adjacentPosn).getCard();
        if (adjacentCard != null && adjacentCard.getOwner() != playerType) {
          int attackValue = card.getAttackValue(direction);
          int defenseValue = adjacentCard.getAttackValue(direction.oppositeDirection());
          if (attackValue > defenseValue) {
            countFlips++;
          }
        }
      }
    }
    return countFlips;
  }

  /**
   * Retrieves a list of positions on the grid that are available for placing a card. A position is
   * considered available if it is playable and does not currently contain a card.
   *
   * @param model the readonly model providing access to the grid and cell information
   * @return a list of available positions where a card can be placed on the grid
   */
  private List<Posn> getAvailablePositions(ReadonlyThreeTriosModel model) {
    List<Posn> availablePositions = new ArrayList<>();
    int rows = model.getGrid().getRows();
    int cols = model.getGrid().getCols();

    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        Posn pos = new Posn(col, row);
        if (model.getCell(pos).isPlayable() && model.getCell(pos).getCard() == null) {
          availablePositions.add(pos);
        }
      }
    }
    return availablePositions;
  }

  /**
   * Determines whether the given position is preferred over the current best position. A position
   * is considered preferred if it is either the first position evaluated (null bestPosition), has a
   * lower row index (y-coordinate), or has the same row index but a lower column index
   * (x-coordinate).
   *
   * @param position     the position being evaluated for preference
   * @param bestPosition the current best position for comparison
   * @return {@code true} if the given position is preferred over the bestPosition; {@code false}
   *     otherwise
   */
  private boolean isPreferredPosition(Posn position, Posn bestPosition) {
    return bestPosition == null
        || position.getY() < bestPosition.getY()
        || (position.getY() == bestPosition.getY() && position.getX() < bestPosition.getX());
  }

  /**
   * Calculates the position adjacent to the given position in the specified direction.
   *
   * @param posn      the current position on the grid
   * @param direction the direction in which to find the adjacent position
   * @return the position adjacent to {@code posn} in the specified {@code direction}
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
   * Finds and returns the upper-left-most position from a list of available positions. The
   * upper-left-most position is defined as the position with the lowest row index (y-coordinate)
   * and, in the case of a tie, the lowest column index (x-coordinate).
   *
   * @param availablePositions a list of available positions to evaluate
   * @return the upper-left-most position among the available positions
   * @throws IllegalArgumentException if {@code availablePositions} is null or empty
   */
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

}

