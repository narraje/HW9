package cs3500.hw05.model;


import cs3500.hw05.card.CardAdapter;
import cs3500.hw05.card.ICard;
import cs3500.hw05.model.grid.IGrid;
import cs3500.hw05.model.grid.cell.CellAdapter;
import cs3500.hw05.model.grid.cell.ICell;
import cs3500.hw05.player.IPlayer;
import cs3500.hw05.player.PlayerAdapter;
import cs3500.threetrios.providers.model.Grid;
import cs3500.threetrios.providers.model.Model;
import cs3500.threetrios.providers.model.ModelListener;
import java.util.ArrayList;
import java.util.List;
import cs3500.threetrios.providers.model.Card;
import cs3500.threetrios.providers.model.Cell;
import cs3500.threetrios.providers.model.Posn;
import cs3500.threetrios.providers.model.enums.PlayerColor;
import cs3500.threetrios.providers.player.Player;

/**
 * Adapter class for our provider's model methods. These are so they can be
 * used later on in their view implementation.
 */
public class ModelAdapter extends GameModel implements Model {
  /**
   * Constructor for the model adapter, which initializes the model.
   * @param model our implementation of the model.
   */
  public ModelAdapter(IModel model) {
    super(model.getGrid(), model.getDeck(), model.getGrid().getLayout(),
        model.getPlayer1(), model.getPlayer2());
  }
  @Override
  public List<Card> getHand(Player player) {
    IPlayer ourPlayer = player.getColor() == PlayerColor.RED
        ? this.getPlayer1()
        : this.getPlayer2();

    List<ICard> ourHand = ourPlayer.getHand();
    List<Card> theirHand = new ArrayList<>();

    for (ICard card : ourHand) {
      theirHand.add(new CardAdapter(card));
      }
    return theirHand;
  }

  @Override
  public List<Card> getHand(PlayerColor playerColor) {
    IPlayer ourPlayer = playerColor == PlayerColor.RED
        ? this.getPlayer1()
        : this.getPlayer2();

    List<ICard> ourHand = ourPlayer.getHand();
    List<Card> theirHand = new ArrayList<>();
    for (ICard card : ourHand) {
      theirHand.add(new CardAdapter(card));
    }
    return theirHand;
  }

  @Override
  public boolean isGameOver() {
    return super.isGameOver();
  }

  @Override
  public List<Player> whoWon() {
    return null;
    //no uses
  }

  /**
   * This is used in the GUI view extensively,
   * especially in the gridPanel.
   *
   * @return the 2D array of cells (the grid)
   */
  @Override
  public Cell[][] getGridArray() {
    Cell[][] layout = new Cell[this.getRows()][this.getCols()];
    for (int row = 0; row < getGrid().getRows(); row++) {
      for (int col = 0; col < getGrid().getCols(); col++) {
        ICell cell = this.getCell(new cs3500.hw05.model.grid.Posn(col, row));
        if (this.getCell(new cs3500.hw05.model.grid.Posn(col, row)).isPlayable()) {
          layout[row][col] = new CellAdapter(cell, new cs3500.hw05.model.grid.Posn(col, row));
        }
      }
    }
    return layout;
  }

  @Override
  public Player getCurPlayer() {
    //not used in view
    return null;
  }

  @Override
  public int howManyCardsFlip(Posn coordinate, Card card) {
    //not used in view
    return 0;
  }

  @Override
  public int getPlayerScore(Player player) {
    //not used in view;
    return 0;
  }

  @Override
  public void setupGame(boolean shuffle, int numPlayers, List<Card> deck, Grid grid) {
    //not used in view;
  }

  @Override
  public void startGame() {
    //not used in view;
  }

  @Override
  public void addPlayer(Player player) {
    //not used in view;
  }

  @Override
  public void playMove(Posn position, int cardIdx) {
    //not used in view;
  }

  @Override
  public void addModelListener(ModelListener listener) {
    //not used in view;
  }
}
