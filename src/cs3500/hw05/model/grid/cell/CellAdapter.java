package cs3500.hw05.model.grid.cell;

import java.awt.*;

import cs3500.hw05.card.ICard;
import cs3500.hw05.player.PlayerType;
import cs3500.threetrios.providers.model.Card;
import cs3500.threetrios.providers.model.Cell;
import cs3500.threetrios.providers.model.Posn;

/**
 * An adapter class for a Cell in the game Three Trios. This cell
 */
public class CellAdapter implements Cell {

  private final ICell cell;

  public CellAdapter(ICell cell) {
    this.cell = cell;
  }
  @Override
  public Color getCellColor() {
    if (cell.isPlayable() && cell.getCard() != null) {
      return mapPlayerColor(cell.getCard().getOwner());
    }
    if (cell.isPlayable()) {
      return Color.LIGHT_GRAY;
    }
    return Color.DARK_GRAY;
  }

  private Color mapPlayerColor(cs3500.hw05.player.PlayerType type) {
    switch (type) {
      case RED:
        return Color.RED;
      case BLUE:
        return Color.BLUE;
      default:
        return Color.GRAY;
    }
  }

  @Override
  public Posn getPosition() {
    return null;
    //not used
  }

  @Override
  public Card getCard() {
    return null;
    //not used
  }
}
