package cs3500.hw05.view.gui.handpanel;

import cs3500.hw05.card.ICard;
import java.awt.Point;
import java.util.List;

/**
 * Defines the required behaviors for a hand panel in the view for the game Three Trios. Displays
 * the cards in a player's hand and manages interactions such as selecting and highlighting cards
 * based on user clicks.
 */
public interface IHandPanel {

  /**
   * Sets the hand of cards to be displayed in the panel. Updates the panel with the current list of
   * cards in the player's hand, which allows the view to accurately reflect the player's current
   * available cards.
   *
   * @param hand the list of cards to be displayed.
   */
  void setHand(List<ICard> hand);

  /**
   * Clears the current card selection in the hand panel. After calling this method, no card will be
   * highlighted as selected.
   */
  void clearSelection();

  /**
   * Sets the index of the selected card in the hand panel. Highlights the card at the specified
   * index to indicate selection.
   *
   * @param cardIndex the index of the card to select and highlight
   */
  void setSelectedCardIndex(int cardIndex);

  /**
   * Determines the index of the card located at a given screen coordinate point. Useful for
   * detecting which card a user has clicked on or hovered over.
   *
   * @param point the {@link Point} representing the screen coordinates to check
   * @return the index of the card at the specified point, or -1 if no card is at the point
   */
  int getCardIndex(Point point);

  /**
   * Retrieves a read-only copy of the hand of cards.
   *
   * @return an unmodifiable list of cards representing the player's hand.
   */
  List<ICard> getReadOnlyHand();

  /**
   * Sets the listener in a hand panel to be the one passed in as an input.
   * @param listener the lister to be set.
   */
  void setHandPanelListener(HandPanelListener listener);
}
