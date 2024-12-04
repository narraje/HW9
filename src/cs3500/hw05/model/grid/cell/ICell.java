package cs3500.hw05.model.grid.cell;

import cs3500.hw05.card.ICard;

/**
 * interface that represents a cell of the grid for third times a charm.
 */
public interface ICell {

  /**
   * Checks if this cell can hold a card.
   *
   * @return true if this cell can hold a card, false otherwise (e.g., if it's a Hole).
   */
  boolean isPlayable();

  /**
   * Gets the card currently placed in this cell.
   *
   * @return the card if one is present, otherwise null.
   */
  ICard getCard();

  /**
   * Places a card in this cell.
   *
   * @param card the card to place in the cell.
   * @throws IllegalArgumentException if this cell cannot hold a card.
   */
  void setCard(ICard card);

  /**
   * Creates a deep copy of this cell.
   *
   * @return a new ICell that is a deep copy of this cell.
   */
  ICell copy();


  /**
   * Flips the owner of the card in this cell.
   *
   * @throws IllegalStateException if there is no card in the cell or the cell is not playable.
   */
  void flipCardOwner();
}

