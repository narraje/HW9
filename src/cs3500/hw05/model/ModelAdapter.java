package cs3500.hw05.model;


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
public class ModelAdapter implements Model {

  private final ReadonlyThreeTriosModel gameModel;
  private final List<Player> players;
  private Grid grid;

  /**
   * Constructor for the model adapter, which initializes the model.
   * @param gameModel our implementation of the model.
   */
  public ModelAdapter(ReadonlyThreeTriosModel gameModel, Grid grid) {
    this.gameModel = gameModel;
    this.grid = grid;
    this.players = new ArrayList<>();
  }
  @Override
  public List<Card> getHand(Player player) {
    return null;
    //not used;
  }

  @Override
  public List<Card> getHand(PlayerColor playerColor) {
    return null;
    //not used
  }

  @Override
  public boolean isGameOver() {
    return false;
    //no uses
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
    return new Cell[0][];
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
