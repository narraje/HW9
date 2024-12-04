package cs3500.hw05.view.gui.gridpanel;

import cs3500.hw05.model.grid.Posn;

/**
 * Represents a listener for the game Three Trios. Behavior includes notifying the user when a card
 * is placed on the grid.
 */
public interface GridPanelListener {

  /**
   * Receives input in the case that a card has been placed on the grid.
   * @param pos the position where the card was placed on the grid.
   */
  void onPlaceCard(Posn pos);
}
