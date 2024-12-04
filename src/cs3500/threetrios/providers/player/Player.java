package cs3500.threetrios.providers.player;

import cs3500.threetrios.providers.controller.ClickListener;
import cs3500.threetrios.providers.model.enums.PlayerColor;

/**
 * Represents an entity who is playing the Three Trios game. A Player can be either human
 * or robotic, but their allowed behavior is described by this interface.
 */
public interface Player {
  /**
   * Determine the color that is used to represent this Player.
   * @return this Player's PlayerColor
   */
  PlayerColor getColor();

  /**
   * Play a move based on the state of the game in this Player's interpretation of the game state.
   */
  void playMove();

  /**
   * Set up a listener to handle click events by this Player.
   * @param listener the listener for events in this ThreeTrio game
   */
  void addClickListener(ClickListener listener);

  /**
   * Determine the String that represents this Player when being rendered in a text view.
   * @return a String representation of this Player
   */
  String toString();
}
