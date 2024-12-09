package cs3500.hw05.model;

import cs3500.hw05.card.ICard;
import cs3500.hw05.model.battlerule.CompositeRule;
import cs3500.hw05.model.battlerule.FallenAce;
import cs3500.hw05.model.battlerule.PlusRule;
import cs3500.hw05.model.battlerule.Reverse;
import cs3500.hw05.model.battlerule.SameRule;
import cs3500.hw05.model.configs.CardDatabaseParser;
import cs3500.hw05.model.configs.GridConfigParser;
import cs3500.hw05.model.grid.IGrid;
import cs3500.hw05.player.AiPlayer;
import cs3500.hw05.player.IPlayer;
import cs3500.hw05.player.Player;
import cs3500.hw05.player.PlayerType;
import cs3500.hw05.strategy.Strategy;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * A Builder class to create instances of the game Three Trios. Human players can be added
 * to the game, or AI players with their own strategies.
 */
public class GameBuilder {

  private IGrid grid;
  private List<ICard> deck;
  private char[][] layout;
  private IPlayer player1;
  private IPlayer player2;

  private boolean use1AiPlayer = false;
  private boolean use2AiPlayer = false;
  private Strategy aiStrategy1;
  private Strategy aiStrategy2;

  /**
   * Empty constructor for the game builder, where no AI strategies are used.
   */
  public GameBuilder() {
    //no AI strategies are used in this instance of a game being built.
  }

  /**
   * Configures the first player as an AI with a specific strategy.
   *
   * @param strategy the strategy for the first AI player
   * @return the updated GameBuilder instance
   */
  public GameBuilder with1AiPlayer(Strategy strategy) {
    this.use1AiPlayer = true;
    this.aiStrategy1 = strategy;
    return this;
  }

  /**
   * Configures the second player as an AI with a specific strategy.
   *
   * @param strategy the strategy for the second AI player
   * @return the updated GameBuilder instance
   */
  public GameBuilder with2AiPlayer(Strategy strategy) {
    this.use2AiPlayer = true;
    this.aiStrategy2 = strategy;
    return this;
  }

  /**
   * Loads configuration files to initialize the game grid and card deck for both players.
   * This method parses the specified grid and card files, creating a `Grid` layout from the grid
   * file and constructing a deck of `Card` objects for both players.
   *
   * @param gridFilePath  the path to the file containing the grid configuration
   * @param cardsFilePath the path to the file containing the cards
   * @return the updated `GameBuilder` instance with grid and deck initialized
   * @throws FileNotFoundException if any of the specified files cannot be found
   */
  public GameBuilder fromConfigFiles(String gridFilePath, String cardsFilePath)
      throws FileNotFoundException {
    this.grid = GridConfigParser.parseGridConfig(gridFilePath);
    this.layout = grid.getLayout();

    List<ICard> cards = CardDatabaseParser.parseCardDatabase(cardsFilePath);

    // Determine player 1
    if (use1AiPlayer) {
      player1 = new AiPlayer(PlayerType.RED, aiStrategy1, new ArrayList<>());
    } else {
      player1 = new Player(PlayerType.RED, new ArrayList<>());
    }

    // Determine player 2
    if (use2AiPlayer) {
      player2 = new AiPlayer(PlayerType.BLUE, aiStrategy2, new ArrayList<>());
    } else {
      player2 = new Player(PlayerType.BLUE, new ArrayList<>());
    }

    this.deck = new ArrayList<>(cards);
    return this;
  }

  /**
   * Builds and returns a configured GameModel instance.
   *
   * @return a new GameModel instance
   */
  public GameModel build() {
    return new GameModel(grid, deck, layout, player1, player2, List.of(new PlusRule()));
  }
}

