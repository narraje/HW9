package cs3500.hw05.view.gui.gridpanel;

import cs3500.hw05.card.ICard;
import cs3500.hw05.model.ReadonlyThreeTriosModel;
import cs3500.hw05.card.Direction;
import cs3500.hw05.model.grid.Posn;
import cs3500.hw05.player.PlayerType;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;

/**
 * Represents the main grid view for the game Three Trios.
 * This panel displays the game grid, including holes and playable cells,
 * and handles rendering of cards placed on the grid.
 */
public class GridPanel extends JPanel implements IGridPanel {

  private List<List<Boolean>> cellTypes; // true if hole, false if playable cell
  private ReadonlyThreeTriosModel model;
  private int numRows;
  private int numCols;
  private int cellWidth;
  private int cellHeight;
  private GridPanelListener listener;

  /**
   * Constructs a new {@code GridPanel} and initializes its components. Sets up the preferred size,
   * tool tip manager, and mouse listener for interaction.
   */
  public GridPanel() {
    this.cellTypes = new ArrayList<>();
    setPreferredSize(new Dimension(800, 600));
    ToolTipManager.sharedInstance().registerComponent(this);

    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (cellTypes.isEmpty()) {
          return;
        }
        calculateGridMetrics();
        int gridCol = e.getX() / cellWidth;
        int gridRow = e.getY() / cellHeight;

        if (gridRow >= 0 && gridRow < numRows && gridCol >= 0 && gridCol < numCols) {
          System.out.println("Grid cell clicked: Row = " + gridRow + ", Column = " + gridCol);
          listener.onPlaceCard(new Posn(gridRow, gridCol));
        } else {
          System.out.println("Clicked outside grid bounds");
        }
      }
    });
  }

  /**
   * Initializes the grid based on the provided model. This method sets up the cell types (holes or
   * playable cells) according to the grid layout.
   *
   * @param model the readonly model containing the grid layout
   */
  @Override
  public void initializeGrid(ReadonlyThreeTriosModel model) {
    this.model = model;
    char[][] layout = model.getGrid().getLayout();
    int rows = model.getGrid().getRows();
    int cols = model.getGrid().getCols();

    cellTypes = new ArrayList<>();
    for (int r = 0; r < rows; r++) {
      List<Boolean> rowTypes = new ArrayList<>();
      for (int c = 0; c < cols; c++) {
        if (layout[r][c] == 'H') {
          rowTypes.add(true);
        } else {
          rowTypes.add(false);
        }
      }
      cellTypes.add(rowTypes);
    }
    calculateGridMetrics();
  }

  /**
   * Calculates the grid metrics such as the number of rows and columns, and the width and height of
   * each cell based on the panel size.
   */
  private void calculateGridMetrics() {
    numRows = cellTypes.size();
    numCols = cellTypes.isEmpty() ? 0 : cellTypes.get(0).size();

    if (numRows == 0 || numCols == 0) {
      return;
    }

    cellWidth = getWidth() / numCols;
    cellHeight = getHeight() / numRows;
  }

  /**
   * Overrides the {@code paintComponent} method to draw the grid and the cards. This method is
   * called whenever the panel needs to be repainted.
   *
   * @param g the {@code Graphics} object used for drawing
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    if (cellTypes.isEmpty()) {
      return;
    }

    calculateGridMetrics();

    Graphics2D g2d = (Graphics2D) g;

    for (int gridRow = 0; gridRow < numRows; gridRow++) {
      for (int gridCol = 0; gridCol < numCols; gridCol++) {
        boolean isHole = cellTypes.get(gridRow).get(gridCol);

        if (isHole) {
          g2d.setColor(Color.GRAY);
        } else {
          g2d.setColor(Color.YELLOW);
        }

        // Calculate screen positions
        int screenX = gridCol * cellWidth;
        int screenY = gridRow * cellHeight;

        g2d.fillRect(screenX, screenY, cellWidth, cellHeight);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(screenX, screenY, cellWidth, cellHeight);
      }
    }
    drawCardsOnGrid(g2d);
  }

  /**
   * Draws the cards that are placed on the grid. Iterates through the grid positions and renders
   * any cards found.
   *
   * @param g2d the {@code Graphics2D} object used for drawing
   */
  private void drawCardsOnGrid(Graphics2D g2d) {
    for (int gridRow = 0; gridRow < numRows; gridRow++) {
      for (int gridCol = 0; gridCol < numCols; gridCol++) {
        ICard card = model.getGrid().getCell(new Posn(gridRow, gridCol)).getCard();

        if (card != null) {
          int cardX = gridCol * cellWidth; // Use gridCol for horizontal position (X-axis)
          int cardY = gridRow * cellHeight; // Use gridRow for vertical position (Y-axis)


          drawCard(g2d, card, cardX, cardY);
        }
      }
    }
  }

  /**
   * Draws a single card at the specified screen coordinates. The card is colored based on its owner
   * and displays attack values on each side.
   *
   * @param g2d     the {@code Graphics2D} object used for drawing
   * @param card    the card to draw
   * @param screenX the x-coordinate on the screen where the card should be drawn
   * @param screenY the y-coordinate on the screen where the card should be drawn
   */
  private void drawCard(Graphics2D g2d, ICard card, int screenX, int screenY) {
    g2d.setColor(card.getOwner() == PlayerType.RED ? Color.RED : Color.BLUE);

    // Draw the card rectangle filling the cell
    g2d.fillRect(screenX, screenY, cellWidth, cellHeight);

    // Draw the card border
    g2d.setColor(Color.BLACK);
    g2d.drawRect(screenX, screenY, cellWidth, cellHeight);

    int fontSize = Math.min(cellWidth, cellHeight) / 5;
    Font attackValueFont = new Font("Arial", Font.BOLD, fontSize);
    g2d.setFont(attackValueFont);
    g2d.setColor(Color.BLACK);

    String northAbbr = card.getAttackAbbreviation(Direction.North);
    String southAbbr = card.getAttackAbbreviation(Direction.South);
    String eastAbbr = card.getAttackAbbreviation(Direction.East);
    String westAbbr = card.getAttackAbbreviation(Direction.West);

    FontMetrics fm = g2d.getFontMetrics();
    int textWidth = fm.stringWidth(northAbbr);
    int textHeight = fm.getHeight();

    int centerX = screenX + cellWidth / 2;
    int centerY = screenY + cellHeight / 2;

    // North
    g2d.drawString(northAbbr, centerX - textWidth / 2, screenY + textHeight);
    // South
    g2d.drawString(southAbbr, centerX - textWidth / 2, screenY + cellHeight - 5);
    // East
    g2d.drawString(eastAbbr, screenX + cellWidth - textWidth - 5, centerY + textHeight / 2);
    // West
    g2d.drawString(westAbbr, screenX + 5, centerY + textHeight / 2);
  }

  /**
   * Provides tool tip text when the mouse hovers over a cell containing a card. Displays the card's
   * attack values for each direction.
   *
   * @param event the {@code MouseEvent} triggering the tooltip.
   * @return the tooltip text displaying the card's attack values, or {@code null} if no card is
   *     present.
   */
  @Override
  public String getToolTipText(MouseEvent event) {
    if (cellTypes.isEmpty()) {
      return null;
    }

    calculateGridMetrics();

    int gridCol = event.getX() / cellWidth;
    int gridRow = event.getY() / cellHeight;

    if (gridRow >= 0 && gridRow < numRows && gridCol >= 0 && gridCol < numCols) {
      ICard card = model.getGrid().getCell(new Posn(gridRow, gridCol)).getCard();
      if (card != null) {
        StringBuilder tooltipText = new StringBuilder("<html>");
        tooltipText.append("<b>Card Attacks:</b><br>");
        tooltipText.append("North: ").append(card.getAttackValue(Direction.North)).append("<br>");
        tooltipText.append("East: ").append(card.getAttackValue(Direction.East)).append("<br>");
        tooltipText.append("South: ").append(card.getAttackValue(Direction.South)).append("<br>");
        tooltipText.append("West: ").append(card.getAttackValue(Direction.West)).append("<br>");
        tooltipText.append("</html>");
        return tooltipText.toString();
      }
    }
    return null;
  }


  public void setGridPanelListener(GridPanelListener listener) {
    this.listener = listener;
  }
}




