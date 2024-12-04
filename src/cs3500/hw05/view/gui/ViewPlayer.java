package cs3500.hw05.view.gui;

import cs3500.hw05.model.ReadonlyThreeTriosModel;
import cs3500.hw05.model.grid.Posn;
import cs3500.hw05.player.IPlayer;
import cs3500.hw05.player.PlayerController;
import cs3500.hw05.player.PlayerType;
import cs3500.hw05.view.gui.gridpanel.GridPanel;
import cs3500.hw05.view.gui.gridpanel.GridPanelListener;
import cs3500.hw05.view.gui.handpanel.HandPanel;
import cs3500.hw05.view.gui.handpanel.HandPanelListener;
import cs3500.hw05.view.gui.handpanel.IHandPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

/**
 * Represents the GUI view for the game Three Trios. Provides a UI to display the game state, and
 * consistently updates the display to match any changes being made in the game state. Consists of a
 * main grid panel for the game board and two hand panels, one on each side, to represent each
 * player's hand. It also includes a label which indicates the current player, updating as the game
 * progresses.
 */
public class ViewPlayer extends JFrame implements IThreeTriosView, HandPanelListener,
    GridPanelListener {

  private final JLabel currentPlayerLabel;
  private final GridPanel gridPanel;
  private final IHandPanel leftHand;
  private final IHandPanel rightHand;
  private final JScrollPane leftHandPanel;
  private final JScrollPane rightHandPanel;
  private final JPanel bottomBuffer;
  private final List<PlayerController> observers = new ArrayList<>();

  /**
   * Constructor for the Swing/GUI view of Three Trios using a ReadOnly model, and sets up the
   * layout and other components for the GUI. Initializes the main grid and hand panels, so it
   * displays the initial game state that the model provides.
   *
   * @param model the ReadOnly Model representing the game state.
   */
  public ViewPlayer(ReadonlyThreeTriosModel model) {
    setTitle("Three Trios Game");
    setLayout(new BorderLayout());
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    currentPlayerLabel = new JLabel("Current Player: "
        + model.getCurrentPlayer().getPlayerType());
    bottomBuffer = new JPanel();
    bottomBuffer.setPreferredSize(new Dimension(0, 20));

    gridPanel = new GridPanel();
    gridPanel.initializeGrid(model);
    gridPanel.setGridPanelListener(this);

    IPlayer player1 = model.getPlayer1();
    IPlayer player2 = model.getPlayer2();

    leftHand = new HandPanel(Color.RED, player1);
    rightHand = new HandPanel(Color.BLUE, player2);
    leftHand.setHand(model.getPlayer1().getHand());
    rightHand.setHand(model.getPlayer2().getHand());

    leftHand.setHandPanelListener(this);
    rightHand.setHandPanelListener(this);

    leftHandPanel = createHandScrollPane(leftHand);
    rightHandPanel = createHandScrollPane(rightHand);

    setupLayout();
    pack();

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int screenWidth = screenSize.width;
    int screenHeight = screenSize.height;
    setSize(screenWidth, screenHeight);
    int minWidth = (int) (screenWidth * 0.5);
    int minHeight = (int) (screenHeight * 0.5);
    setMinimumSize(new Dimension(minWidth, minHeight));

    setLocationRelativeTo(null);
    setVisible(true);
  }

  @Override
  public void refresh() {
    repaint();
  }


  /**
   * Sets up the layout for the main view by adding key components to specific positions within the
   * BorderLayout.
   */
  private void setupLayout() {
    add(currentPlayerLabel, BorderLayout.NORTH);
    add(leftHandPanel, BorderLayout.WEST);
    add(gridPanel, BorderLayout.CENTER);
    add(rightHandPanel, BorderLayout.EAST);
    add(bottomBuffer, BorderLayout.SOUTH);
  }

  /**
   * Creates a scrollable pane for a player's hand panel, configured to display a vertical scroll
   * bar when necessary while disabling horizontal scrolling. This method sets the scroll bar's
   * preferred size and scrolling speed to ensure smooth scrolling behavior for large hands.
   *
   * @param handPanel the panel displaying the player's hand, to be wrapped in a JScrollPane for
   *                  scrolling support if the hand exceeds the visible area.
   * @return a JScrollPane configured to display the player's hand panel with vertical scrolling
   *     enabled.
   */
  private JScrollPane createHandScrollPane(IHandPanel handPanel) {
    JScrollPane scrollPane = new JScrollPane((Component) handPanel);

    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

    JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
    verticalBar.setPreferredSize(new Dimension(0, 0));
    verticalBar.setUnitIncrement(10);

    return scrollPane;
  }

  /**
   * Retrieve the hand panel on the left side of the view.
   * @return the IHandPanel of the player on the left side of the screen.
   */
  public IHandPanel getLeftHandPanel() {
    return this.leftHand;
  }

  @Override
  public void showTurnIndicator(IPlayer currentPlayer) {
    SwingUtilities.invokeLater(() ->  {
      String message = "Player " + currentPlayer.getPlayerType() + ", it is your turn!";
      JOptionPane.showMessageDialog(this, message, "Your Turn",
          JOptionPane.INFORMATION_MESSAGE);
    });
  }

  @Override
  public void showGameOver(IPlayer winner, int winnerScore) {
    SwingUtilities.invokeLater(() -> {
      String message = "Game Over!\n"
          +
              "Winner: Player " + winner.getPlayerType() + " with score: " + winnerScore + "\n";
      JOptionPane.showMessageDialog(this, message, "Game Over",
              JOptionPane.INFORMATION_MESSAGE);
    });
  }


  /**
   * Retrieve the hand panel on the right side of the view.
   * @return the IHandPanel of the player on the right side of the screen.
   */
  public IHandPanel getRightHandPanel() {
    return this.rightHand;
  }

  @Override
  public void onCardSelected(int cardIndex, IPlayer player) {
    for (PlayerController obs : observers) {
      obs.selectCard(cardIndex, player);
    }
  }

  @Override
  public void addObserver(PlayerController observer) {
    observers.add(observer);
  }

  @Override
  public void onPlaceCard(Posn pos) {
    for (PlayerController obs : observers) {
      obs.placeCard(pos);
    }
  }

  @Override
  public void updateCurrentPlayerLabel(PlayerType currentPlayer) {
    SwingUtilities.invokeLater(() -> {
      currentPlayerLabel.setText("Current Player: " + currentPlayer);
    });
  }

  @Override
  public void showError(String errorMessage) {
    SwingUtilities.invokeLater(() -> {
      JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    });
  }


}

