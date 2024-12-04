package cs3500.hw05.model.grid;
import cs3500.threetrios.providers.model.Card;
import cs3500.threetrios.providers.model.Cell;
import cs3500.threetrios.providers.model.Grid;
import cs3500.threetrios.providers.model.Posn;
import cs3500.threetrios.providers.player.Player;

/**
 * An adapter for the provider's Grid interface. Designed to work with our implementation of the
 * IGrid interface.
 */
public class GridAdapter implements Grid {

  @Override
  public Cell[][] getGridArray() {
    return null;
    //no uses
  }

  @Override
  public void setCardInGrid(Posn pos, Card card) {
    //not used
  }

  @Override
  public void battle(Posn pos) {
    //not used
  }

  @Override
  public int howManyCardsFlip(Posn coordinate, Card card) {
    return 0;
    //not used
  }

  @Override
  public int getNumFreeCardCells() {
    return 0;
    //not used
  }

  @Override
  public int playerScore(Player player) {
    return 0;
    //not used
  }
}
