package cs3500.threetrios.providers.view;

import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

import cs3500.threetrios.providers.model.Card;
import cs3500.threetrios.providers.model.enums.Direction;

/**
 * This class represents a viewable Card on the game's window.
 */
public class ThreeTrioCardPanel {
  private final Card card;

  /**
   * Constructor for the ThreeTrioCardPanel class.
   * @param card the Card that needs to be represented (viewed) on the game frame
   */
  public ThreeTrioCardPanel(Card card) {
    this.card = card;
  }

  /**
   * Draw the actual card onto the View at the given x, y positions.
   * @param g2d the Graphics2D object that the card is drawn onto
   * @param x the grid's x value where the card is drawn
   * @param y the grid's y value where the card is drawn
   */
  public void drawCard(Graphics2D g2d, int x, int y, boolean highlightedCard) {
    // Initialize the line and line width, and store the original transform size
    BasicStroke bs;
    if (highlightedCard) {
      bs = new BasicStroke(0.05f);
    } else {
      bs = new BasicStroke(0.01f);
    }
    Path2D.Double path = new Path2D.Double();
    AffineTransform oldTransform = g2d.getTransform();

    // Draw the rectangle representing the card
    path.moveTo(x, y);
    path.lineTo(x + 1, y);
    path.lineTo(x + 1, y + 1);
    path.lineTo(x, y + 1);
    path.closePath();

    // Set the card's fill color to the corresponding player
    g2d.setColor(card.getColor().toRealColor());
    g2d.fill(path);

    // Draw the outline of the card
    g2d.setStroke(bs);
    g2d.setColor(Color.BLACK);
    g2d.draw(path);

    // Draw the Card's Rank values
    drawRank(g2d, x, y);

    // Reset to the original transform
    g2d.setTransform(oldTransform);
  }

  /**
   * Draw the rank values corresponding to this Card.
   * @param g2d the Graphics2D object that the card is drawn onto
   * @param x the grid's x value where the text's offset should be based out of
   * @param y the grid's y value where the text's offset should be based out of
   */
  public void drawRank(Graphics2D g2d, int x, int y) {
    if (card != null) {
      float scale = 0.20f;
      g2d.scale(scale, scale);
      g2d.setFont(new Font("Arial", Font.PLAIN, 1));

      g2d.drawString(card.getRank(Direction.NORTH).toString(),
              (x + 0.44f) / scale, (y + 0.25f) / scale);
      g2d.drawString(card.getRank(Direction.SOUTH).toString(),
              (x + 0.44f) / scale, (y + 0.9f) / scale);
      g2d.drawString(card.getRank(Direction.WEST).toString(),
              (x + 0.1f) / scale, (y + 0.58f) / scale);
      g2d.drawString(card.getRank(Direction.EAST).toString(),
              (x + 0.8f) / scale, (y + 0.58f) / scale);
    }
  }
}
