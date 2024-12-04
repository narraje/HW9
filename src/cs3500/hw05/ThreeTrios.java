package cs3500.hw05;

import cs3500.hw05.model.GameModel;
import cs3500.hw05.model.GameBuilder;
import cs3500.hw05.model.ModelAdapter;
import cs3500.hw05.player.IPlayer;
import cs3500.hw05.player.PlayerController;
import cs3500.hw05.player.AiPlayerController;
import cs3500.hw05.strategy.Strategy;
import cs3500.hw05.strategy.MaxFlips;
import cs3500.hw05.strategy.CornerStrategy;
import cs3500.hw05.view.gui.IThreeTriosView;
import cs3500.hw05.view.gui.ViewAdapter;
import cs3500.hw05.view.gui.ViewPlayer;

import cs3500.threetrios.providers.model.ReadOnlyModel;
import cs3500.threetrios.providers.view.SwingView;
import cs3500.threetrios.providers.view.ThreeTrioSwingView;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Entry point for the Three Trios game application.
 * <p>
 * The {@code ThreeTrios} class initializes and starts the application by launching the game based
 * on command-line arguments.
 * </p>
 */
public class ThreeTrios {

  /**
   * The main method serves as the entry point for the Three Trios application.
   * <p>
   * It parses command-line arguments to configure the players and starts the game.
   * </p>
   *
   * @param args command-line arguments specifying player types and strategies
   */
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    String gridConfig = "configs/grids/Game1";
    String cardsConfig = "configs/cards/cards";

    String[] playerConfigs = getPlayerConfigurations(args, scanner);

    GameModel gameModel = buildGameModel(gridConfig, cardsConfig);

    initializeGame(gameModel, playerConfigs);
  }

  /**
   * Retrieves player configurations either from command-line arguments or by prompting the user.
   *
   * @param args    the command-line arguments
   * @param scanner the {@code Scanner} for user input
   * @return an array containing player configurations
   */
  private static String[] getPlayerConfigurations(String[] args, Scanner scanner) {
    String player1Type;
    String player1StrategyName = null;
    String player2Type;
    String player2StrategyName = null;

    int argIndex = 0;

    if (args.length >= 2) {
      player1Type = args[argIndex++];
      if (player1Type.equalsIgnoreCase("ai")) {
        player1StrategyName = args[argIndex++];
      }

      if (args.length < argIndex + 1) {
        System.err.println("Error: Player 2 type must be specified.");
        System.exit(1);
      }

      player2Type = args[argIndex++];
      if (player2Type.equalsIgnoreCase("ai")) {
        if (args.length < argIndex + 1) {
          System.err.println("Error: Strategy must be specified for AI player 2.");
          System.exit(1);
        }
        player2StrategyName = args[argIndex++];
      }
    } else {
      System.out.println("Command-line arguments not provided or insufficient.");
      System.out.println("Please enter the player configurations.");

      player1Type = promptForPlayerType(scanner, "Player 1");
      if (player1Type.equalsIgnoreCase("ai")) {
        player1StrategyName = promptForStrategy(scanner, "Player 1");
      }

      player2Type = promptForPlayerType(scanner, "Player 2");
      if (player2Type.equalsIgnoreCase("ai")) {
        player2StrategyName = promptForStrategy(scanner, "Player 2");
      }
    }

    return new String[]{player1Type, player1StrategyName, player2Type, player2StrategyName};
  }

  /**
   * Builds the game model based on the specified configuration files.
   *
   * @param gridConfig  the path to the grid configuration file
   * @param cardsConfig the path to the cards configuration file
   * @return the constructed {@code GameModel}
   */
  private static GameModel buildGameModel(String gridConfig, String cardsConfig) {
    GameBuilder gameBuilder = new GameBuilder();

    try {
      gameBuilder.fromConfigFiles(gridConfig, cardsConfig);
    } catch (FileNotFoundException ex) {
      System.err.println("Configuration file not found: " + ex.getMessage());
      System.exit(1);
    }

    return gameBuilder.build();
  }

  /**
   * Initializes and starts the game with the provided configurations.
   *
   * @param gameModel     the game model
   * @param playerConfigs the configurations for the players
   */
  private static void initializeGame(GameModel gameModel, String[] playerConfigs) {
    String player1Type = playerConfigs[0];
    String player1StrategyName = playerConfigs[1];
    String player2Type = playerConfigs[2];
    String player2StrategyName = playerConfigs[3];

    int cellCount = gameModel.getGrid().cellCount();
    int handSize = (cellCount + 1) / 2;


    IThreeTriosView viewPlayer1 = new ViewPlayer(gameModel);
    //IThreeTriosView viewPlayer2 = new ViewPlayer(gameModel);

    SwingView theirViewPlayer2 = new ThreeTrioSwingView(new ModelAdapter(gameModel), "player1");
    IThreeTriosView viewPlayer2 = new ViewAdapter(theirViewPlayer2);

    //PlayerController controller1 = createPlayerController(gameModel, viewPlayer1, player1Type,
     //   player1StrategyName, gameModel.getPlayer1());
    //PlayerController controller2 = createPlayerController(gameModel, viewPlayer2, player2Type,
    //    player2StrategyName, gameModel.getPlayer2());

    gameModel.startGame(false, handSize);
  }

  /**
   * Creates a player controller based on the player type and strategy.
   *
   * @param gameModel    the game model
   * @param viewPlayer   the player's view
   * @param playerType   the type of the player (human/ai)
   * @param strategyName the strategy name if the player is AI
   * @param player       the player instance
   * @return the appropriate {@code PlayerController}
   */
  private static PlayerController createPlayerController(GameModel gameModel,
      IThreeTriosView viewPlayer, String playerType, String strategyName, IPlayer player) {
    if (playerType.equalsIgnoreCase("ai")) {
      Strategy strategy = getStrategyByName(strategyName);
      return new AiPlayerController(gameModel, viewPlayer, player, strategy);
    }
    return new PlayerController(gameModel, viewPlayer, player);
  }

  /**
   * Prompts the user for a valid player type (human or ai).
   *
   * @param scanner the {@code Scanner} for user input
   * @param player  the name of the player (e.g., "Player 1")
   * @return the valid player type
   */
  private static String promptForPlayerType(Scanner scanner, String player) {
    while (true) {
      System.out.printf("Enter %s Type (human/ai): ", player);
      String type = scanner.nextLine().trim();
      if (type.equalsIgnoreCase("human") || type.equalsIgnoreCase("ai")) {
        return type;
      }
      System.out.println("Invalid input. Please enter 'human' or 'ai'.");
    }
  }

  /**
   * Prompts the user for a valid strategy for an AI player.
   *
   * @param scanner the {@code Scanner} for user input
   * @param player  the name of the player (e.g., "Player 1")
   * @return the valid strategy name
   */
  private static String promptForStrategy(Scanner scanner, String player) {
    while (true) {
      System.out.printf("Enter %s Strategy (maxFlips/cornerStrategy): ", player);
      String strategy = scanner.nextLine().trim();
      if (isValidStrategy(strategy)) {
        return strategy;
      }
      System.out.println("Unknown strategy. Please enter 'maxFlips' or 'cornerStrategy'.");
    }
  }

  /**
   * Validates if the given strategy name is supported.
   *
   * @param strategyName the strategy name to validate
   * @return {@code true} if the strategy is valid, otherwise {@code false}
   */
  private static boolean isValidStrategy(String strategyName) {
    return strategyName.equalsIgnoreCase("maxFlips")
        || strategyName.equalsIgnoreCase("cornerStrategy");
  }

  /**
   * Retrieves a Strategy instance based on the strategy name.
   *
   * @param strategyName the name of the strategy
   * @return a Strategy instance
   */
  private static Strategy getStrategyByName(String strategyName) {
    switch (strategyName.toLowerCase()) {
      case "maxflips":
        return new MaxFlips();
      case "cornerstrategy":
        return new CornerStrategy();
      default:
        throw new IllegalArgumentException("Unknown strategy: " + strategyName);
    }
  }
}




