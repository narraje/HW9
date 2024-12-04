package cs3500.threetrios.providers.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.List;
import java.util.Objects;

import javax.swing.JPanel;

import cs3500.threetrios.providers.controller.ClickListener;
import cs3500.threetrios.providers.model.Card;
import cs3500.threetrios.providers.model.ReadOnlyModel;
import cs3500.threetrios.providers.model.enums.PlayerColor;

/**
 * This class represents the viewable game Hands on the game's window. Contains interactive hands.
 */
public class ThreeTrioHandPanel extends JPanel {
  private final ReadOnlyModel model;
  private final PlayerColor playerColor;
  private ClickListener clickListener;
  private int selectedCard;

  /**
   * Constructor for the ThreeTrioHandPanel class.
   * @param model a ReadOnly instance of the ThreeTrio game model for the panel to render
   * @param playerColor the color of the Cards in this panel
   * @throws NullPointerException if the model or playerColor are null
   */
  public ThreeTrioHandPanel(ReadOnlyModel model, PlayerColor playerColor) {
    this.model = Objects.requireNonNull(model);
    this.playerColor = Objects.requireNonNull(playerColor);

    addMouseListener(new ThreeTrioHandListener());
    selectedCard = -1;
  }

  /**
   * Returns the PlayerColor that this Panel corresponds to.
   * @return the PlayerColor of this panel.
   */
  public PlayerColor getPanelColor() {
    return playerColor;
  }

  /**
   * Get the logical size of a Player hand to transform it from a pixel xy to a cell xy.
   * @return the dimensions of the drawable Player hand (in width and height of cells)
   */
  private Dimension getPreferredLogicalHandSize(List<Card> hand) {
    return new Dimension(
            getParent().getWidth() / (model.getGridArray()[0].length + 2),
            hand.size()
    );
  }

  /**
   * Compute the transformation that converts screen coordinates
   * (with (0,0) in upper-left, width and height in pixels)
   * into board coordinates (with (0,0) in upper-left, width and height
   * our logical size corresponding to the width and height of a hand).
   *
   * @param hand hand whose dimension to normalize
   * @return The necessary transformation
   */
  private AffineTransform transformPhysicalToLogicalHand(List<Card> hand) {
    AffineTransform ret = new AffineTransform();
    Dimension preferred = getPreferredLogicalHandSize(hand);
    ret.scale(preferred.getWidth(),
            getHeight() / preferred.getHeight());
    return ret;
  }

  @Override
  public Dimension getPreferredSize() {
    if (getParent() != null) {
      return new Dimension(
              getParent().getWidth() / (model.getGridArray()[0].length + 2),
              getParent().getHeight()
      );
    }

    return new Dimension(100, 480);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();

    List<Card> hand = model.getHand(playerColor);

    AffineTransform oldTransform = g2d.getTransform();

    g2d.transform(transformPhysicalToLogicalHand(hand));
    for (int i = 0; i < hand.size(); i++) {
      Card card = hand.get(i);
      boolean highlight = (i == selectedCard);
      new ThreeTrioCardPanel(card).drawCard(g2d, 0, i, highlight);
    }
    g2d.setTransform(oldTransform);
  }

  /**
   * Add a Controller ClickListener to this view for handling mouse events.
   * @param clickListener the ClickListener to add
   */
  public void setClickListener(ClickListener clickListener) {
    this.clickListener = clickListener;
  }

  /**
   * Select the given CardIdx in this HandPanel (when clicked on).
   * @param cardIdx card index to select (negative or non-negative integer)
   */
  public void selectCard(int cardIdx) {
    if (selectedCard == cardIdx) {
      selectedCard = -1;
    } else {
      selectedCard = cardIdx;
    }
  }

  private class ThreeTrioHandListener extends ThreeTrioClickListener {
    @Override
    public void mousePressed(MouseEvent e) {
      int numCards = model.getHand(playerColor).size();

      int cardHeight = getHeight() / numCards;
      int cardIdx = e.getY() / cardHeight;

      System.out.println(playerColor + " hand Clicked, Index: " + cardIdx);

      if (clickListener != null && cardIdx >= 0 && cardIdx < numCards) {
        clickListener.handleHandClick(playerColor, cardIdx);
      }
    }
  }
}
