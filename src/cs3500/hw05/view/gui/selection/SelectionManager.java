package cs3500.hw05.view.gui.selection;

import cs3500.hw05.card.ICard;
import cs3500.hw05.view.gui.handpanel.IHandPanel;
import java.util.List;

/**
 * A singleton class that manages the selection of cards in the game's hand panels.
 * It ensures that only one card is selected at a time across all hand panels.
 */
public class SelectionManager {
  private static SelectionManager instance;
  private IHandPanel selectedHandPanel;
  private int selectedCardIndex = -1;

  /**
   * Private constructor to prevent instantiation from outside the class.
   * Use {@link #getInstance()} to obtain the singleton instance.
   */
  private SelectionManager() {}

  /**
   * Retrieves the singleton instance of the SelectionManager.
   *
   * @return the single instance of SelectionManager
   */
  public static SelectionManager getInstance() {
    if (instance == null) {
      instance = new SelectionManager();
    }
    return instance;
  }

  /**
   * Sets the selected card in the specified hand panel.
   * If another card was previously selected, it is deselected.
   * If the same card is clicked again, it is deselected.
   *
   * @param handPanel the hand panel containing the selected card
   * @param cardIndex the index of the selected card within the hand panel
   */
  public void setSelectedCard(IHandPanel handPanel, int cardIndex) {
    if (selectedHandPanel != null && selectedHandPanel != handPanel) {
      selectedHandPanel.clearSelection();
    }

    if (this.selectedHandPanel == handPanel && this.selectedCardIndex == cardIndex) {
      // Deselect if the same card is clicked again
      handPanel.clearSelection();
      this.selectedHandPanel = null;
      this.selectedCardIndex = -1;
    } else {
      this.selectedHandPanel = handPanel;
      this.selectedCardIndex = cardIndex;
      handPanel.setSelectedCardIndex(cardIndex);
    }
  }

  /**
   * Returns the currently selected card in the Hand Panel.
   * @return the ICard that is currently selected.
   */
  public ICard getSelectedCard() {
    if (selectedHandPanel == null || selectedCardIndex < 0) {
      return null;
    }

    List<ICard> hand = selectedHandPanel.getReadOnlyHand();
    if (selectedCardIndex >= 0 && selectedCardIndex < hand.size()) {
      return hand.get(selectedCardIndex);
    }
    return null;
  }

  /**
   * Remove the selection of any card in a hand panel.
   */
  public void clearSelectedCard() {
    selectedHandPanel.clearSelection();
  }
}


