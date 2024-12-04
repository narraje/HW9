package cs3500.threetrios.providers.controller;

import cs3500.threetrios.providers.model.Posn;
import cs3500.threetrios.providers.model.enums.PlayerColor;

/**
 *  This interface represents an action a Player can take. Can be interfaced through a JSwing View
 *  or origniated from a Player.
 */
public interface ClickListener {
  /**
   * Handle an action in one of the hands, such as selecting and playing a card on the grid.
   * @param cardIdx the index of the card that was clicked
   * @param color the color of the hand that is selected
   */
  void handleHandClick(PlayerColor color, int cardIdx);

  /**
   * Handle an action in a single cell of the grid, such as to make a move (place a card down).
   * @param cardIdx the row, col of the clicked Cell.
   */
  void handleGridClick(Posn cardIdx);
}
