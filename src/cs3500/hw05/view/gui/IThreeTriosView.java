package cs3500.hw05.view.gui;

import cs3500.hw05.player.IPlayer;
import cs3500.hw05.player.PlayerController;
import cs3500.hw05.player.PlayerType;
import cs3500.hw05.view.gui.handpanel.IHandPanel;

/**
 * Represents the behaviors required for the view of the view of Three Trios, using a GUI to
 * display the game state.
 */
public interface IThreeTriosView {


  /**
   * Refreshes the view and updates all displayed components. Ensures that any
   * changes in the game state are visually reflected in the GUI.
   */
  void refresh();

  /**
   * Displays an indicator of who's turn it currently is.
   *
   * @param currentPlayer the current player whose turn it is.
   */
  void showTurnIndicator(IPlayer currentPlayer);

  /**
   * Displays a message declaring that the game is over and announces the winner.
   * @param winner the player who is the winner.
   * @param winnerScore the winner's score, which is the amount of cards in their color.
   */
  void showGameOver(IPlayer winner, int winnerScore);

  /**
   * Retrieves the right-hand panel displaying the hand of cards for one player.
   *
   * @return the right-hand panel of the GUI.
   */
  IHandPanel getRightHandPanel();

  /**
   * Retrieves the left-hand panel displaying the hand of cards for one player.
   *
   * @return the left-hand panel of the GUI.
   */
  IHandPanel getLeftHandPanel();

  /**
   * Adding a PlayerController as an observer to the view.
   * @param observer the PlayerController to be added.
   */
  void addObserver(PlayerController observer);

  /**
   * Updates the label at the top of the GUI to display which player's turn it currently is.
   * @param currentPlayer the player whose turn it currently is.
   */
  void updateCurrentPlayerLabel(PlayerType currentPlayer);

  /**
   * Display a message when some invalid action occurs.
   * @param errorMessage the message to be displayed on the screen.
   */
  void showError(String errorMessage);
}
