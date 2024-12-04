package cs3500.hw05.strategy;

import cs3500.hw05.card.ICard;
import cs3500.hw05.model.grid.Posn;
import cs3500.hw05.model.ReadonlyThreeTriosModel;
import cs3500.hw05.player.PlayerType;

/**
 * Behaviors for a Strategic Computer Player playing the game Three Trios. A non-human player would
 * have the ability to determine which card to choose, and where to place that card.
 */
public interface Strategy {

  /**
   * Chooses the best card to play based on the current game state and player's hand.
   *
   * @param model  the current game model for analyzing the state of the game
   * @param player the player making the move
   * @return the chosen Card to play
   */
  ICard chooseCard(ReadonlyThreeTriosModel model, PlayerType player);

  /**
   * Selects the best position on the grid for the chosen card based on the current game state.
   *
   * @param model  the current game model for analyzing the grid and opponent cards
   * @param player the player making the move
   * @param card   the card that the player intends to place on the grid
   * @return the Posn representing the best position for the chosen card
   * @throws IllegalArgumentException if model or card are null.
   */
  Posn choosePosition(ReadonlyThreeTriosModel model, PlayerType player, ICard card);


}
