package cs3500.threetrios.providers.model;

import cs3500.threetrios.providers.model.enums.CardRank;
import cs3500.threetrios.providers.model.enums.Direction;
import cs3500.threetrios.providers.model.enums.PlayerColor;

/**
 * This Card interface represents the behavior of a card in the game Three Trios.
 *
 */
public interface Card {

  /**
   * Determines if this Card is of higher rank (greater than) the other Card in the specified
   * Direction.
   * @param other the Card to compare this Card to.
   * @param dir the side of this Card on which the other Card is located. For example, if the other
   *            Card is located to the right of this Card, dir should be set to East.
   * @return true iff the rank of this card at the given Direction is greater than the rank of the
   *         other Card in the opposite of the given Direction.
   */
  boolean isGreaterThan(Card other, Direction dir);

  /**
   * Determines the CardRank of this Card at the given Direction.
   * @param dir the Direction that corresponds to the Card's CardRank that needs to be fetched.
   * @return the CardRank of this Card at the given Direction.
   */
  CardRank getRank(Direction dir);

  /**
   * Determines the PlayerColor of this card.
   * @return the PlayerColor that the color of this Card is set to.
   */
  PlayerColor getColor();

  /**
   * Transfers ownership of this Card to the given Player. If the Card is already designated as
   * "owned" by the given Player in some way, nothing happens.
   * @param player the Player to designate as the owner of this Card.
   */
  void setOwner(PlayerColor player);

  /**
   * Represents this Card as a String.
   * @return the String representation of this Card.
   */
  String toString();
}
