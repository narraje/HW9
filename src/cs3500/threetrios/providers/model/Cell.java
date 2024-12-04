package cs3500.threetrios.providers.model;

import java.awt.Color;

/**
 * Although this implementation of a cell on a grid may seem redundant, it allows for a complete
 * representation of playable cells on the game grid. It will be used to aid in rendering a view
 * and any other instance where a complete representation of a Grid cell is necessary.
 */
public interface Cell {

  /**
   * Determines what color this cell should be colored in as.
   * @return the java.awt.Color that this cell should be represented by.
   */
  Color getCellColor();

  /**
   * Determines the position of this cell, in two-dimensional coordinates.
   * This coordinate system is the model's coordinate system, so please refer to the
   * definition of the Model and Grid interfaces for further breakdown.
   * @return the location in 2D space that this cell occupies, in model coordinates.
   */
  Posn getPosition();

  /**
   * Determines the String representation of this Cell, to be used for text views and debugging.
   * @return the string representation of this cell.
   */
  String toString();

  /**
   * Return the Card associated with this cell (if any is present).
   * @return the Card in this cell, is null when there is no Card in the cell
   */
  Card getCard();
}
