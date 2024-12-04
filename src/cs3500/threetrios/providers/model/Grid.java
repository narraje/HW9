package cs3500.threetrios.providers.model;


import cs3500.threetrios.providers.player.Player;

/**
 * Represents the behavior of a Grid of Cards. Since most of the visible game happens on the Grid,
 * the Grid is the part of the model that is most tasked with maintaining the current state of the
 * game while allowing the rest of the model to update it correctly. Additionally, the Grid's
 * visual aspect means it is also able to return a copy of itself in the form of a 2D array of
 * Cells.
 */
public interface Grid {

  /**
   * Constructs and returns a deep copy of this Grid, complete with HoleCells, and filled and
   * unfilled CardCells.
   * @return this Grid, represented as a 2D array of cells. To be used for rendering and debugging.
   */
  Cell[][] getGridArray();

  /**
   * Sets Card located at the given Point to be the given Card.
   * @param pos the point that corresponds to the given Card
   * @param card the Card to set this Point to.
   * @throws IllegalArgumentException if given Point is already assigned to a Card, or if the given
   *         Point does not exist in this Grid.
   */
  void setCardInGrid(Posn pos, Card card);

  /**
   * Given a Point that corresponds to a Card at which to start battling at,
   * this method runs the battle phase of the Three Trios game that is described
   * in Section 5.2 of the assignment.
   * @param pos the Position at which to start battling at.
   */
  void battle(Posn pos);

  /**
   * Determines the number of cards that would be flipped if the given Card was played to the given
   * coordinate.
   * @param coordinate the Posn coordinate that the given card will be played to.
   * @param card the Card that will be played to the given coordinate.
   * @return the number of cards that playing the given Card to the given Posn would flip.
   */
  int howManyCardsFlip(Posn coordinate, Card card);

  /**
   * Determines how many spaces this Grid has that can hold Cards.
   * @return the number of spaces that can hold Cards in this grid.
   */
  int getNumFreeCardCells();

  /**
   * Determines the given Player's current score based on how many Cards they have of their color
   * on the Grid and in their hand.
   * @param player the Player to determine the current score of.
   * @return a number representing the number of the given Player's tiles in the game.
   */
  int playerScore(Player player);
}
