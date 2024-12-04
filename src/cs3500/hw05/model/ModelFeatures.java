package cs3500.hw05.model;

import cs3500.hw05.player.IPlayer;

/**
 * Represents features for the model to communicate to the controller.
 */
public interface ModelFeatures {

  /**
   * Communicates that it is a players turn, after move is made, to the controller.
   *
   * @param currentPlayer the current player.
   */
  void onTurn(IPlayer currentPlayer);

  /**
   * Communicates to the controller that the game is over.
   *
   * @param winner is the winner of the game.
   */
  void onGameOver(IPlayer winner);
}
