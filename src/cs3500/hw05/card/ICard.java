package cs3500.hw05.card;

import cs3500.hw05.player.PlayerType;

/**
 * Interface representing a card in the game Three Trios, holding responsibilities of
 * maintaining information about ownership, attack values, and a unique name.
 */
public interface ICard {

  /**
   * Gets the unique name or identifier of the card.
   *
   * @return the name of the card
   */
  String getName();

  /**
   * Retrieves the attack value on the specified side of the card.
   *
   * @param direction the direction to retrieve the attack value for (NORTH, SOUTH, EAST, WEST)
   * @return the attack value on the specified side
   * @throws IllegalArgumentException if direction is null
   */
  int getAttackValue(Direction direction);

  /**
   * Creates a copy of this card for encapsulated usage.
   *
   * @return a new Card object that is a copy of this card
   */
  Card copy();

  /**
   * Gets the player type (owner) of this card.
   *
   * @return the PlayerType of the card's owner
   */
  PlayerType getOwner();

  /**
   * Sets the player type (owner) of this card.
   *
   * @param owner the PlayerType to set as the owner of the card
   */
  void setOwner(PlayerType owner);

  /**
   * Retrieves the abbreviated attack value for a specified direction, suitable for display.
   *
   * @param direction the direction for which the attack abbreviation is needed
   * @return a string representation of the attack abbreviation for the specified direction
   */
  String getAttackAbbreviation(Direction direction);
}

