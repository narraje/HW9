package cs3500.hw05.view.gui;


import cs3500.hw05.model.grid.Posn;
import cs3500.hw05.player.IPlayer;

/**
 * Defines the set of actions available to interact with in the game Three Trios.
 * Implemented by the controller, while allowing the view to trigger game actions such as
 * selecting cards, interacting with the grid, and managing player turns.
 */
public interface Features {
  /**
   * Selects a card in the player's hand to be played.
   *
   * @param cardIndex the index of the card in the player's hand.
   */
  void selectCard(int cardIndex, IPlayer player);

  /**
   * Places the selected card in the specified grid position.
   *
   * @param position the position on the grid where the card should be placed.
   */
  void placeCard(Posn position);
}
