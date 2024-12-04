/**
 * Deprecated class for current assignment.
 * Will be included in the future; functionality includes
 * displaying a window before starting the game allowing
 * the user to configure the type of game with the grid, cards,
 * and ai players.
 */

/*
package cs3500.hw05.view;

import cs3500.hw05.model.GameBuilder;
import cs3500.hw05.model.GameModel;
import cs3500.hw05.player.AiPlayerController;
import cs3500.hw05.player.PlayerController;
import cs3500.hw05.strategy.CornerStrategy;
import cs3500.hw05.strategy.LeastFlippable;
import cs3500.hw05.strategy.MaxFlips;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * A window that allows the user to select game configurations for grids and cards.
 * This window provides dropdown menus to select configurations and a button to start the game
 * with the selected configurations.
public class GameSelectionWindow extends JFrame {

  private final JComboBox<String> gridComboBox;
  private final JComboBox<String> cardsComboBox;
  private JCheckBox player1AIBox;
  private JCheckBox player2AIBox;

  /**
   * Constructs the GameSelectionWindow, initializing the GUI components and setting up the layout.
   * This window allows the user to select grid and card configurations and start the game.

  public GameSelectionWindow() {
    setTitle("Select Game Configuration");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new GridBagLayout());
    setResizable(false);

    JLabel gridLabel = new JLabel("Select Grid Configuration:");
    gridComboBox = new JComboBox<>(getConfigFiles());

    JLabel cardsLabel = new JLabel("Select Cards Configuration:");
    cardsComboBox = new JComboBox<>(getConfigFiles("configs/cards", "cards"));

    player1AIBox = new JCheckBox("Player 1 is AI");
    player2AIBox = new JCheckBox("Player 2 is AI");

    JButton startButton = new JButton("Start Game");

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;

    gbc.gridx = 0;
    gbc.gridy = 0;
    add(gridLabel, gbc);
    gbc.gridx = 1;
    add(gridComboBox, gbc);

    gbc.gridx = 0;
    gbc.gridy = 1;
    add(cardsLabel, gbc);
    gbc.gridx = 1;
    add(cardsComboBox, gbc);

    gbc.gridx = 0;
    gbc.gridy = 2;
    add(player1AIBox, gbc);
    gbc.gridx = 1;
    add(player2AIBox, gbc);

    gbc.gridx = 0;
    gbc.gridy = 3;
    gbc.gridwidth = 2;
    add(startButton, gbc);

    startButton.addActionListener(e -> {
      boolean player1IsAI = player1AIBox.isSelected();
      boolean player2IsAI = player2AIBox.isSelected();
      startGameWithSelectedConfiguration(player1IsAI, player2IsAI);
    });

    pack();
    setLocationRelativeTo(null);
    setVisible(true);
  }

  /**
   * Retrieves the list of grid configuration files from the default directory.
   *
   * @return an array of grid configuration file names

  private String[] getConfigFiles() {
    return getConfigFiles("configs/grids",
            null);
  }

  /**
   * Retrieves a list of configuration files from the specified directory, optionally filtering
   * by a prefix.
   *
   * @param directoryPath the path to the directory containing configuration files
   * @param prefixFilter  an optional prefix to filter the files; if null, no filtering is applied
   * @return an array of configuration file names

  private String[] getConfigFiles(String directoryPath, String prefixFilter) {
    File directory = new File(directoryPath);
    String[] files = directory.list((dir, name) -> {
      if (name.startsWith(".")) {
        return false;
      }
      if (prefixFilter != null) {
        return name.startsWith(prefixFilter);
      }
      return true;
    });
    return files != null ? files : new String[0];
  }

  /**
   * Starts the game using the selected grid and card configurations.
   * Builds the game model, starts the game, and opens the main game window.
   * If configuration files are not found, displays an error message.
   *
   * <p>This method also makes some initial moves to set up the game state.</p>

  private void startGameWithSelectedConfiguration(boolean player1IsAI, boolean player2IsAI) {
    String gridConfig = "configs/grids/" + gridComboBox.getSelectedItem();
    String cardsConfig = "configs/cards/" + cardsComboBox.getSelectedItem();

    GameBuilder gameBuilder = new GameBuilder();

    try {
      if (player1IsAI) {
        gameBuilder.with1AiPlayer(new MaxFlips()); // Example AI strategy
      }
      if (player2IsAI) {
        gameBuilder.with2AiPlayer(new CornerStrategy()); // Example AI strategy
      }
      gameBuilder.fromConfigFiles(gridConfig, cardsConfig);
    } catch (FileNotFoundException ex) {
      JOptionPane.showMessageDialog(this, "Configuration file not found: " + ex.getMessage(),
          "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    GameModel gameModel = gameBuilder.build();
    int cellCount1 = gameModel.getGrid().cellCount();
    int handSize = (cellCount1 + 1) / 2;

    ViewPlayer viewPlayer1 = new ViewPlayer(gameModel);
    ViewPlayer viewPlayer2 = new ViewPlayer(gameModel);

    PlayerController controller1;
    if (player1IsAI) {
      controller1 = new AiPlayerController(gameModel, viewPlayer1, gameModel.getPlayer1(),
          new MaxFlips());
    } else {
      controller1 = new PlayerController(gameModel, viewPlayer1, gameModel.getPlayer1());
    }

    PlayerController controller2;
    if (player2IsAI) {
      controller2 = new AiPlayerController(gameModel, viewPlayer2, gameModel.getPlayer2(),
          new LeastFlippable());
    } else {
      controller2 = new PlayerController(gameModel, viewPlayer2, gameModel.getPlayer2());
    }


    gameModel.startGame(false, handSize);

    this.dispose();
  }
}
*/


