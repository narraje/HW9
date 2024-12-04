package cs3500.hw05.model.grid.cell;

import cs3500.hw05.card.ICard;
import cs3500.hw05.player.PlayerType;

/**
 * Represents a cell on the game grid that can hold a card. A CardCell is a playable cell where.
 * players can place their cards, allowing interactions during gameplay, such as battles and.
 * ownership flips.
 */
public class CardCell implements ICell {

  private ICard card;

  /**
   * Constructs an empty CardCell with no card initially placed.
   */
  public CardCell() {
    this.card = null;
  }

  @Override
  public boolean isPlayable() {
    return true;
  }

  @Override
  public ICard getCard() {
    return this.card;
  }

  @Override
  public void setCard(ICard card) {
    if (card == null) {
      throw new IllegalArgumentException("Card cannot be null");
    }
    this.card = card;
  }


  @Override
  public ICell copy() {
    CardCell newCell = new CardCell();
    if (this.card != null) {
      newCell.setCard(this.card.copy());
    }
    return newCell;
  }

  @Override
  public void flipCardOwner() {
    if (this.card == null) {
      throw new IllegalStateException("No card to flip.");
    }
    PlayerType currentOwner = this.card.getOwner();
    PlayerType newOwner = (currentOwner == PlayerType.RED) ? PlayerType.BLUE : PlayerType.RED;
    this.card.setOwner(newOwner);
  }

}
