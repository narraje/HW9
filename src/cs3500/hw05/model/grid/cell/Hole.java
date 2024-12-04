package cs3500.hw05.model.grid.cell;

import cs3500.hw05.card.ICard;

/**
 * representation of a cell that is empty or a "hole", and this.
 * cell is unplayable on the grid.
 */
public class Hole implements ICell {

  @Override
  public boolean isPlayable() {
    return false;
  }

  @Override
  public ICard getCard() {
    return null;
  }

  @Override
  public void setCard(ICard card) {
    throw new UnsupportedOperationException("Cannot place a card in a Hole.");
  }

  @Override
  public ICell copy() {
    return new Hole();
  }

  @Override
  public void flipCardOwner() {
    throw new UnsupportedOperationException("Cannot flip card in a Hole.");
  }
}
