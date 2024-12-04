package cs3500.threetrios.providers.view;

import cs3500.threetrios.providers.controller.ClickListener;
import cs3500.threetrios.providers.model.enums.PlayerColor;

/**
 * Represents a Java Swing view of a Three Trio game.
 */
public interface SwingView extends View {
  /**
   * Set up the controller to handle click events in this view.
   * @param listener the controller
   */
  void addClickListener(ClickListener listener);

  /**
   * Refresh the view to reflect any changes in the game state.
   */
  void refresh();

  /**
   * Refresh the view and indicate which player is active (whose turn it is).
   * @param active if this panel is the active panel (whose turn it is)
   */
  void refresh(boolean active);

  /**
   * Make the view visible to start the game session.
   * @param show determines if this frame gets set to visible or not
   */
  void makeVisible(boolean show);

  /**
   * Highlight the selected Card in the active hand.
   * @param cardIdx the index of the card in hand to highlight
   */
  void highlightSelectedCard(int cardIdx);

  /**
   * Lock the View to only interact with its fields who share the given PlayerColor.
   * @param playerColor The PlayerColor is playerColor
   */
  void setActivePanel(PlayerColor playerColor);

  /**
   * Show the given message on this SwingView.
   * @param title the title of this message
   * @param contents the text contents of this message
   * @param errorMessage true iff this is an error message
   */
  void showMessage(String title, String contents, boolean errorMessage);
}
