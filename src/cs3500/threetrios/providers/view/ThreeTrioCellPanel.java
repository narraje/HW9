package cs3500.threetrios.providers.view;

import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

import cs3500.threetrios.providers.model.Card;
import cs3500.threetrios.providers.model.Cell;
import cs3500.threetrios.providers.model.Posn;

/**
 * This class represents the View of a ThreeTrio Cell, the displayable representation of a
 *     ThreeTrioCell. Contains the coordinate rules and methods to draw a cell and its color
 *     and contents in a scalable manner.
 */
public class ThreeTrioCellPanel {
  private final Cell cell;

  /**
   * Constructor for the ThreeTrioCellPanel.
   * @param cell to be drawn into the View
   */
  public ThreeTrioCellPanel(Cell cell) {
    this.cell = cell;
  }

  /**
   * Draw the cell into the given Graphics2D view object.
   * @param g2d Graphics2D object to draw the cell into
   */
  public void drawCell(Graphics2D g2d) {
    // Initialize the line and line width, and store the original transform size
    BasicStroke bs = new BasicStroke(0.01f);
    Path2D.Double path = new Path2D.Double();
    AffineTransform oldTransform = g2d.getTransform();

    // Offset the starting position by 1 (since the hand takes the first column in the grid)
    Posn pos = cell.getPosition();
    int row = pos.getY();
    int col = pos.getX();

    // Draw the rectangle representing the card
    path.moveTo(row, col);
    path.lineTo(row + 1, col);
    path.lineTo(row + 1, col + 1);
    path.lineTo(row, col + 1);
    path.closePath();

    // Set the cell's fill color accordingly
    g2d.setColor(cell.getCellColor());
    g2d.fill(path);

    // Draw the outline of the cell
    g2d.setStroke(bs);
    g2d.setColor(Color.BLACK);
    g2d.draw(path);

    // If the Cell contains a Card, draw the Card's Rank values
    Card cellCard = cell.getCard();
    if (cellCard != null) {
      new ThreeTrioCardPanel(cellCard).drawRank(g2d, row, col);
    }

    // Reset to the original transform
    g2d.setTransform(oldTransform);
  }
}
