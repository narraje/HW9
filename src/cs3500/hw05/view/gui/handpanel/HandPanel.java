package cs3500.hw05.view.gui.handpanel;

import cs3500.hw05.card.Direction;
import cs3500.hw05.card.ICard;
import cs3500.hw05.player.PlayerType;
import cs3500.hw05.player.IPlayer;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.Random;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.ToolTipManager;

/**
 * Represents the panel displaying a player's hand in the game Three Trios.
 * Manages the layout and display of cards in a player's hand, as well as
 * handling interactions such as selecting and highlighting cards.
 * Displays each card with its attack values and uses colors to distinguish
 * players from one another.
 */
public class HandPanel extends JPanel implements IHandPanel {

  private List<ICard> hand;
  private int selectedCardIndex = -1;
  private Color randomBorderColor;
  private final Random random = new Random();
  private final int CARD_WIDTH = 130;
  private final int CARD_HEIGHT = 100;
  private final boolean isSelectable = true;
  private HandPanelListener listener;

  /**
   * Creates a new HandPanel for the player's hand, setting up an initial
   * background color and a preferred size for the panel.
   *
   * @param cardColor the background color for a player's cards
   * @param player    the PlayerType representing the player owning this hand
   */
  public HandPanel(Color cardColor, IPlayer player) {
    setBackground(Color.LIGHT_GRAY); // Background color for contrast
    setPreferredSize(getPreferredSize());
    ToolTipManager.sharedInstance().registerComponent(this);

    addMouseMotionListener(new MouseAdapter() {
      @Override
      public void mouseMoved(MouseEvent e) {
        int cardIndex = getCardIndex(e.getPoint());
        if (cardIndex != -1 && hand != null && cardIndex < hand.size()) {
          setToolTipText(hand.get(cardIndex).getName());
        } else {
          setToolTipText(null);
        }
      }
    });

    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (!isSelectable) {
          return;
        }
        int cardIndex = getCardIndex(e.getPoint());
        if (cardIndex != -1 && hand != null && cardIndex < hand.size()) {
          //notifyControllerOfSelectCard(cardIndex, player);
          listener.onCardSelected(cardIndex, player);
          System.out.println("Card clicked: Index = " + cardIndex + ", Player = " + player);
        }
      }
    });

    Timer timer = new Timer(100, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        float randomHue = random.nextFloat();
        randomBorderColor = Color.getHSBColor(randomHue, 1.0f, 1.0f);
        repaint();
      }
    });
    timer.start();
  }

  /**
   * Sets the hand of cards to be displayed in this panel.
   *
   * @param hand the list of {@link ICard} objects representing the player's hand
   */
  @Override
  public void setHand(List<ICard> hand) {
    this.hand = hand;
    repaint();
  }

  /**
   * Clears the current card selection in the hand panel.
   */
  @Override
  public void clearSelection() {
    selectedCardIndex = -1;
    repaint();
  }

  /**
   * Sets the index of the selected card in the hand.
   *
   * @param cardIndex the index of the card to be highlighted as selected
   */
  @Override
  public void setSelectedCardIndex(int cardIndex) {
    this.selectedCardIndex = cardIndex;
    repaint();
  }

  /**
   * Paints the component by drawing the cards in the hand and highlighting
   * the selected card if any.
   *
   * @param g the {@code Graphics} object used for drawing
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (hand == null || hand.isEmpty()) {
      return;
    }
    Graphics2D g2d = (Graphics2D) g;
    int cardScreenX = 10;
    int cardScreenY = 10;

    //Font originalFont = g2d.getFont();
    Font attackValueFont = new Font("Arial", Font.BOLD, 35);

    for (int cardIndex = 0; cardIndex < hand.size(); cardIndex++) {
      ICard card = hand.get(cardIndex);
      if (card != null) {
        g2d.setColor(card.getOwner() == PlayerType.RED ? Color.RED : Color.BLUE);

        g2d.fillRect(cardScreenX, cardScreenY, CARD_WIDTH, CARD_HEIGHT);

        g2d.setColor(Color.BLACK);
        g2d.drawRect(cardScreenX, cardScreenY, CARD_WIDTH, CARD_HEIGHT);

        String northAbbr = card.getAttackAbbreviation(Direction.North);
        String southAbbr = card.getAttackAbbreviation(Direction.South);
        String eastAbbr = card.getAttackAbbreviation(Direction.East);
        String westAbbr = card.getAttackAbbreviation(Direction.West);

        g2d.setFont(attackValueFont);
        g2d.setColor(Color.BLACK);

        g2d.drawString(northAbbr, cardScreenX + CARD_WIDTH / 2 - 5, cardScreenY + 30);
        g2d.drawString(southAbbr, cardScreenX + CARD_WIDTH / 2 - 5, cardScreenY + CARD_HEIGHT - 5);
        g2d.drawString(eastAbbr, cardScreenX + CARD_WIDTH - 30, cardScreenY + CARD_HEIGHT / 2 + 10);
        g2d.drawString(westAbbr, cardScreenX + 15, cardScreenY + CARD_HEIGHT / 2 + 10);

        cardScreenY += CARD_HEIGHT;
      }
    }

    // Highlight the selected card
    if (selectedCardIndex >= 0 && selectedCardIndex < hand.size()) {
      int highlightY = 10 + (CARD_HEIGHT * selectedCardIndex);

      g2d.setColor(randomBorderColor);
      g2d.setStroke(new BasicStroke(4));

      g2d.drawRect(cardScreenX - 2, highlightY - 2, CARD_WIDTH + 4, CARD_HEIGHT + 4);
    }

    g2d.dispose();
  }

  /**
   * Returns the preferred size of this component.
   *
   * @return the preferred {@link Dimension} of the hand panel
   */
  @Override
  public Dimension getPreferredSize() {
    int cardCount = (hand != null) ? hand.size() : 0;
    int totalHeight = 10 + (cardCount * CARD_HEIGHT) + 10;
    return new Dimension(CARD_WIDTH + 20, totalHeight);
  }

  /**
   * Determines the index of the card at the given point.
   *
   * @param point the {@link Point} where the mouse event occurred
   * @return the index of the card at the specified point, or -1 if none
   */
  @Override
  public int getCardIndex(Point point) {
    if (hand == null || hand.isEmpty()) {
      return -1;
    }

    int cardScreenY = 10;

    for (int cardIndex = 0; cardIndex < hand.size(); cardIndex++) {
      if (point.y >= cardScreenY && point.y < cardScreenY + CARD_HEIGHT) {
        return cardIndex;
      }
      cardScreenY += CARD_HEIGHT;
    }
    return -1;
  }

  @Override
  public List<ICard> getReadOnlyHand() {
    return Collections.unmodifiableList(this.hand);
  }

  @Override
  public void setHandPanelListener(HandPanelListener listener) {
    this.listener = listener;
  }
}


