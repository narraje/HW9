package cs3500.threetrios.providers.model;

import java.util.List;

import cs3500.threetrios.providers.player.Player;

/**
 * Represents the behavior of a mutable Three Trio game.
 */
public interface Model extends ReadOnlyModel {
  /**
   * After most of this Model's values in the constructor, this method is called to begin the
   *    gameplay.
   * @param shuffle whether to shuffle the deck of cards in this game or not
   * @param numPlayers the number of Players in this game of Three Trio
   * @param deck list of cards to use in the ThreeTrio game
   * @param grid ThreeTrioGrid configuration for this ThreeTrio game
   * @throws IllegalArgumentException if the given numPlayers is invalid
   * @throws IllegalArgumentException if the give deck does not have enough cards for the grid
   */
  void setupGame(boolean shuffle, int numPlayers, List<Card> deck, Grid grid);

  /**
   * Start this game of Three Trios.
   */
  void startGame();

  /**
   * Add a Player to this Model of a Three Trio game.
   * @param player the Player to add.
   */
  void addPlayer(Player player);

  /**
   * Play a move to a point on the Grid. This method includes both the placing and battle phases.
   * @param position the Point position on the grid that is being played to.
   * @param cardIdx the index of Card in hand that is being played to that position.
   */
  void playMove(Posn position, int cardIdx);

  /**
   * Set up the listener to handle events published by the model.
   * @param listener the listener to whatever this Model object publishes.
   */
  void addModelListener(ModelListener listener);
}
