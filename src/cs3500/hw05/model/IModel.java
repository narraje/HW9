package cs3500.hw05.model;

import cs3500.hw05.player.PlayerController;
import cs3500.hw05.player.PlayerType;
import cs3500.hw05.model.grid.Posn;

/**
 * Represents the model of the game Three Trios.
 * The Model holds the responsibility of the game's core logic and managing the state of the game,
 * including initializing the game, processing moves by the player, and determining when the game
 * is over, as well as a winner/tie.
 */
public interface IModel extends ReadonlyThreeTriosModel {

  /**
   * Starts the game by dealing cards to players and setting the initial game state.
   * @throws IllegalStateException if the game has already started.
   * @throws IllegalArgumentException if handSize is null.
   * @throws IllegalArgumentException if the deck doesn't have enough cards for the hand.
   */
  void startGame(boolean shuffle, int handSize);

  /**
   * Processes a player's move, placing the card and executing the battle phase.
   * @param cardName the card to be processed.
   * @param pos the position of card in move
   * @param playerType the player making the move.
   * @throws IllegalStateException if the game has not started yet.
   * @throws IllegalArgumentException if it's not the playerType's turn.
   * @throws IllegalArgumentException if the move is invalid.
   */
  void makeMove(String cardName, Posn pos, PlayerType playerType);

  /**
   * Adding a PlayerController to be an observer of the Model.
   * @param obs the PlayerController to be added.
   */
  void addObserver(PlayerController obs);
}
