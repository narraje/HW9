package cs3500.threetrios.providers.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.util.Objects;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import cs3500.threetrios.providers.controller.ClickListener;
import cs3500.threetrios.providers.model.ReadOnlyModel;
import cs3500.threetrios.providers.model.enums.PlayerColor;

/**
 * This class represents the View of a ThreeTrio Game instance. Outlines the rules in order to
 *    display the game onto an application window.
 */
public class ThreeTrioSwingView extends JFrame implements SwingView {
  private final ThreeTrioHandPanel leftHandPanel;
  private final ThreeTrioHandPanel rightHandPanel;
  private final ThreeTrioGridPanel gridPanel;
  private final String playerName;
  private ThreeTrioHandPanel activeHand;
  private int activeCard;

  /**
   * Constructor for the ThreeTrioSwingView.
   * @param model the ReadOnly ThreeTrio game model to render.
   */
  public ThreeTrioSwingView(ReadOnlyModel model, String playerName) {
    System.out.println("CALLS VIEW CONSTRUCTOR");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.playerName = Objects.requireNonNull(playerName);

    leftHandPanel = new ThreeTrioHandPanel(model, PlayerColor.RED);
    rightHandPanel = new ThreeTrioHandPanel(model, PlayerColor.BLUE);
    gridPanel = new ThreeTrioGridPanel(model);

    add(leftHandPanel, BorderLayout.WEST);
    add(gridPanel, BorderLayout.CENTER);
    add(rightHandPanel, BorderLayout.EAST);

    setMinimumSize(new Dimension(640, 480));
    pack();

    activeCard = -1;
    activeHand = null;
    setTitle(playerName);
  }

  @Override
  public void addClickListener(ClickListener listener) {
    leftHandPanel.setClickListener(listener);
    rightHandPanel.setClickListener(listener);
    gridPanel.setClickListener(listener);
  }

  @Override
  public void refresh() {
    revalidate();
    repaint();

    gridPanel.revalidate();
    leftHandPanel.revalidate();
    rightHandPanel.revalidate();

    pack();

    gridPanel.repaint();
    leftHandPanel.repaint();
    rightHandPanel.repaint();
  }

  @Override
  public void refresh(boolean active) {
    refresh();
    if (active) {
      setTitle("(YOUR TURN) " + playerName);
    }
    else {
      setTitle(playerName);
    }
  }

  @Override
  public void makeVisible(boolean show) {
    refresh();
    setVisible(show);
  }

  @Override
  public void render() throws IOException {
    try {
      makeVisible(true);
    }
    catch (Exception e) {
      throw new IOException("Could not render the Game! Error: " + e.getMessage());
    }
  }

  @Override
  public void highlightSelectedCard(int cardIdx) {
    if (activeCard != cardIdx) {
      activeCard = cardIdx;
      activeHand.selectCard(cardIdx);
    }
    else {
      activeHand.selectCard(-1);
    }
    refresh();
  }

  @Override
  public void setActivePanel(PlayerColor playerColor) {
    if (leftHandPanel.getPanelColor() == playerColor) {
      activeHand = leftHandPanel;
    }
    else {
      activeHand = rightHandPanel;
    }
  }

  @Override
  public void showMessage(String title, String contents, boolean errorMessage) {
    if (errorMessage) {
      JOptionPane.showMessageDialog(this,
              contents, title, JOptionPane.ERROR_MESSAGE);
    }
    else {
      JOptionPane.showMessageDialog(this,
              contents, title, JOptionPane.INFORMATION_MESSAGE);
    }
  }
}
