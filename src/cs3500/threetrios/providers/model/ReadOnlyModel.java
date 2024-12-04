package cs3500.threetrios.providers.model;

import java.util.List;

import cs3500.threetrios.providers.model.enums.PlayerColor;
import cs3500.threetrios.providers.player.Player;

/**
 * Represents the behavior of an immutable Three Trio game.
 */
public interface ReadOnlyModel {
  /**
   * Output the hand of the given Player, in the form of a List of Cards.
   *    Cards in the hand are indexed starting from 0, in either the top-down direction
   *    or right-to-left depending on how the list is visualized.
   * @param player the Player to get the hand from
   * @return a List of Cards representing the hand of the given Player
   */
  List<Card> getHand(Player player);

  /**
   * Output the hand of the given Player, in the form of a List of Cards.
   *    Cards in the hand are indexed starting from 0, in either the top-down direction
   *    or right-to-left depending on how the list is visualized.
   * @param playerColor the PlayerColor of the Player to get the hand from
   * @return a List of Cards representing the hand of the Player with the given PlayerColor
   * @throws IllegalArgumentException if somehow the given PlayerColor is not assigned to a Player
   */
  List<Card> getHand(PlayerColor playerColor);

  /**
   * Determine if this Three Trio game is over, which only occurs when all empty spaces
   *    for Cards in the Grid are filled.
   * @return true iff all empty CardCells are filled
   */
  boolean isGameOver();

  /**
   * Determine which Players won the Three Trio game at the end. If there was a definite winner,
   *    then there is only one Player in the output array. However, if there is a tie, then all
   *    Players who tied for first are present in the output array.
   * @return a List of Players who won the Three Trio game
   * @throws IllegalStateException if called when game is not over
   */
  List<Player> whoWon();

  /**
   * Return a 2D array of Cells representing this Model's playing grid.
   * @return this Three Trio's Grid, represented as a 2D array of Cells
   */
  Cell[][] getGridArray();

  /**
   * Determine the Player whose turn it is currently.
   * @return the Player who is either placing or battling in this Three Two game
   */
  Player getCurPlayer();

  /**
   * Determine the number of cards that would be flipped if the given Card was played to the given
   * coordinate.
   * @param coordinate the Posn coordinate that the given card will be played to
   * @param card the Card that will be played to the given coordinate
   * @return the number of cards that playing the given Card to the given Posn would flip
   */
  int howManyCardsFlip(Posn coordinate, Card card);

  /**
   * Determine and return the score of the passed Player.
   * @return the score of the passed Player
   */
  int getPlayerScore(Player player);
}
