package cs3500.threetrios.providers.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.Objects;

import javax.swing.JPanel;

import cs3500.threetrios.providers.controller.ClickListener;
import cs3500.threetrios.providers.model.Cell;
import cs3500.threetrios.providers.model.Posn;
import cs3500.threetrios.providers.model.ReadOnlyModel;

/**
 * This class represents the viewable game Grid on the game's window. Contains the Playable grid.
 */
public class ThreeTrioGridPanel extends JPanel {
  private final ReadOnlyModel model;
  private ClickListener clickListener;

  /**
   * Constructor for the ThreeTrioGridPanel class.
   * @param model the ReadOnlyModel that represents the current instance (state) of the game
   */
  public ThreeTrioGridPanel(ReadOnlyModel model) {
    this.model = Objects.requireNonNull(model);
    addMouseListener(new ThreeTrioGridListener());
  }

  /**
   * Get the logical size of the grid to transform it from a pixel xy to a cell xy.
   * @return the dimensions of the grid array (in width and height of cells)
   */
  private Dimension getPreferredLogicalSize() {
    return new Dimension(
            model.getGridArray()[0].length,
            model.getGridArray().length
    );
  }

  /**
   * Computes the transformation that converts screen coordinates
   * (with (0,0) in upper-left, width and height in pixels)
   * into board coordinates (with (0,0) in upper-left, width and height
   * our logical size).
   *
   * @return The necessary transformation
   */
  private AffineTransform transformPhysicalToLogical() {
    AffineTransform ret = new AffineTransform();
    Dimension preferred = getPreferredLogicalSize();
    ret.translate(6, 0);
    ret.scale((getWidth() - 12) / preferred.getWidth(), getHeight() / preferred.getHeight());
    return ret;
  }

  @Override
  public Dimension getPreferredSize() {
    int gridWidth = model.getGridArray()[0].length;
    if (getParent() != null) {
      return new Dimension(
              getParent().getWidth() / (gridWidth + 2) * gridWidth,
              getParent().getHeight()
      );
    }

    return new Dimension(400, 480);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();

    AffineTransform oldTransform = g2d.getTransform();

    Cell[][] gridArray = model.getGridArray();

    g2d.transform(transformPhysicalToLogical());
    for (Cell[] cArr : gridArray) {
      for (Cell c : cArr) {
        new ThreeTrioCellPanel(c).drawCell(g2d);
      }
    }

    g2d.setTransform(oldTransform);
  }

  /**
   * Set ClickListener.
   * @param clickListener ClickListener.
   */
  public void setClickListener(ClickListener clickListener) {
    this.clickListener = clickListener;
  }

  private class ThreeTrioGridListener extends ThreeTrioClickListener {
    @Override
    public void mousePressed(MouseEvent e) {
      Cell[][] grid = model.getGridArray();
      int gridWidth = grid[0].length;
      int gridHeight = grid.length;

      int cellHeight = getHeight() / gridHeight;
      int cellWidth = getWidth() / gridWidth;

      Posn cardIdx = new Posn(e.getY() / cellHeight, e.getX() / cellWidth);

      System.out.println("Grid clicked! Row: " + cardIdx.getX() + " Col: " + cardIdx.getY());

      if (clickListener != null && cardIdx.getX() < gridHeight && cardIdx.getY() < gridWidth) {
        clickListener.handleGridClick(cardIdx);
      }
    }
  }
}
