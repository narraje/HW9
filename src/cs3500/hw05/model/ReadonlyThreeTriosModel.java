package cs3500.hw05.model;

import cs3500.hw05.card.ICard;
import cs3500.hw05.model.grid.IGrid;
import cs3500.hw05.model.grid.Posn;
import cs3500.hw05.model.grid.cell.ICell;
import cs3500.hw05.player.IPlayer;
import java.util.List;

/**
 * A Read-Only Interface for the model of Three Trios. Contains the behaviors that allows access to
 * the current game state.
 */
public interface ReadonlyThreeTriosModel {

  /**
   * Checks if the game is over.
   *
   * @return true if all card cells are filled, false otherwise.
   * @throws IllegalStateException if the game has not started.
   */
  boolean isGameOver();

  /**
   * Determines the winner of the game based on the number of owned cards.
   *
   * @return the PlayerType of the winner, or null if the game is a tie.
   * @throws IllegalStateException if the game is not over.
   */
  IPlayer getWinner();

  /**
   * Determines the number of rows in the grid.
   *
   * @return the number of rows in the grid.
   */
  int getRows();

  /**
   * Gets the number of columns in the grid.
   *
   * @return the number of columns in the grid.
   */
  int getCols();

  /**
   * Gets the player whos turn it is to make a move.
   *
   * @return the current Player, indicating which player is to take a turn.
   * @throws IllegalStateException if the game has not yet started.
   */
  IPlayer getCurrentPlayer();

  /**
   * Retrieves the cell at a position in the grid.
   *
   * @param posn is the position of the cell to retrieve.
   * @return the Cell that is located at that specific position.
   * @throws IllegalArgumentException if the position is out of the grid's bounds
   * @throws IllegalArgumentException if posn is null.
   */
  ICell getCell(Posn posn);


  /**
   * retrieves a copy of the grid.
   *
   * @return copy of given grid.
   */
  IGrid getGrid();

  /**
   * Gets the hand of the specified player.
   *
   * @param player the player whose hand to retrieve.
   * @return the list of cards in the player's hand.
   */
  List<ICard> getPlayerHand(IPlayer player);


  /**
   * Returns the first player in the game.
   *
   * <p>This method provides access to one of the two players in the game. The players are
   * assigned as player1 and player2 when the game is initialized, and this method returns the
   * player assigned as player1. The specific order (player1 vs. player2) does not imply any
   * particular meaning unless defined by the game rules.</p>
   *
   * @return the first player in the game
   */
  IPlayer getPlayer1();


  /**
   * Returns the second player in the game.
   *
   * <p>This method provides access to one of the two players in the game. The players are
   * assigned as player1 and player2 when the game is initialized, and this method returns the
   * player assigned as player2. The specific order (player1 vs. player2) does not imply any
   * particular meaning unless defined by the game rules.</p>
   *
   * @return the second player in the game
   */
  IPlayer getPlayer2();

  /**
   * Gets the score of the specified player.
   *
   * @param player the player whose score to retrieve.
   * @return the player's score.
   */
  int getPlayerScore(IPlayer player);
}
