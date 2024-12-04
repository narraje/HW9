package cs3500.hw05.player;

import cs3500.hw05.card.ICard;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents an abstraction of a player, implementing the behaviors of getting the player's hand
 * and PlayerType, as well as setting their hand and removing a card from the hand.
 */
public abstract class AbstractPlayer implements IPlayer {
  protected final PlayerType playerType;
  protected final List<ICard> hand;

  /**
   * Constructor for an abstracted player.
   *
   * @param playerType the playerType of the player.
   * @param initialHand the players hand.
   */
  public AbstractPlayer(PlayerType playerType, List<ICard> initialHand) {
    this.playerType = playerType;
    this.hand = new ArrayList<>(initialHand);
  }

  @Override
  public List<ICard> getHand() {
    return Collections.unmodifiableList(hand);
  }

  @Override
  public PlayerType getPlayerType() {
    return playerType;
  }

  @Override
  public ICard getCardByName(String cardName) {
    for (ICard card : hand) {
      if (card.getName().equals(cardName)) {
        return card;
      }
    }
    return null;
  }

  @Override
  public void setHand(List<ICard> newHand) {
    if (newHand == null) {
      throw new IllegalArgumentException("Hand cannot be null.");
    }
    hand.clear();
    hand.addAll(newHand);
  }

  @Override
  public void removeCard(ICard card) {
    if (!hand.remove(card)) {
      throw new IllegalArgumentException("Card not found in hand.");
    }
  }
}

