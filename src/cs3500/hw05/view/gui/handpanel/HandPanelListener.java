package cs3500.hw05.view.gui.handpanel;

import cs3500.hw05.player.IPlayer;

/**
 * A listener interface to handle card selections in a player's hand panel. The behavior included
 * in this interface is to notify the player when a card is selected in the hand.
 */
public interface HandPanelListener {

  /**
   * Processes the selection of a card in a hand panel.
   * @param cardIndex the index of the card that was selected.
   * @param player the player that selected the card.
   */
  void onCardSelected(int cardIndex, IPlayer player);
}
