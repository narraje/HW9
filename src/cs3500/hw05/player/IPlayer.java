package cs3500.hw05.player;

import cs3500.hw05.card.ICard;
import java.util.List;

/**
 * Represents a player in the game.
 *
 * <p>This interface defines the common behaviors and properties that any player in the game must
 * have. By using an interface, we can implement multiple types of players (e.g., human players, AI
 * players) that can be used interchangeably in the game logic. This design follows the Dependency
 * Inversion and Open/Closed principles, allowing for easy extension of player types without
 * modifying existing code.</p>
 */
public interface IPlayer {

  /**
   * Gets the player's type (e.g., RED, BLUE).
   *
   * @return the player type.
   */
  PlayerType getPlayerType();

  /**
   * Retrieves the player's current hand of cards.
   *
   * @return a list of cards in the player's hand.
   */
  List<ICard> getHand();


  /**
   * Retrieves card by name for usage in move methods.
   *
   * @param cardName name of card.
   * @return card with given name.
   */
  ICard getCardByName(String cardName);

  /**
   * Sets the player's hand to a new list of cards.
   *
   * @param newHand the new list of cards to assign to the player’s hand.
   * @throws IllegalArgumentException if the new hand is null.
   */
  void setHand(List<ICard> newHand);

  /**
   * Removes the specified card from the player's hand.
   *
   * @param card the card to remove from the player's hand
   * @throws IllegalArgumentException if the card is not found in the player's hand
   */
  void removeCard(ICard card);
}
