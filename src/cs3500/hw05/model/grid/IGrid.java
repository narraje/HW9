package cs3500.hw05.model.grid;

import cs3500.hw05.model.grid.cell.ICell;
import cs3500.hw05.player.PlayerType;
import java.util.List;

/**
 * Interface for a representation of a rectangular grid for third times a charm.
 */
public interface IGrid {

  /**
   * Counts the number of playable cells in the grid (e.g., cells where cards can be placed).
   * @return the number of playable cells.
   */
  int cellCount();

  /**
   * counts the numer of cards each player has.
   *
   * @param playerType the type of player.
   * @return the number of cards the given playerType has.
   */
  int countPlayerCards(PlayerType playerType);

  /**
   * Initializes the grid layout with specified dimensions.
   * @param layout the holes and card cells in the layout
   */
  void initializeGrid(char[][] layout);


  /**
   * Checks if a specified position on the grid is a valid, empty card cell.
   * @param posn the position to check.
   * @return true if the position is a valid, empty card cell; false otherwise.
   */
  boolean isValidPosition(Posn posn);

  /**
   * Retrieves adjacent positions for a given position on the grid.
   * @param posn the position to check adjacent cells for.
   * @return a list of adjacent positions on the grid.
   */
  List<Posn> getAdjacentPositions(Posn posn);


  /**
   * Getter for the layout to be used in the builder of the model.
   *
   * @return the layout of the grid.
   */
  char[][] getLayout();

  /**
   * Retrieve the Cell at a certain position on the grid.
   * @param posn the Posn of the cell to be accessed.
   * @return the Cell at the specified posn.
   */
  ICell getCell(Posn posn);

  /**
   * Retrieves the number of rows on the grid.
   * @return the number of rows.
   */
  int getRows();

  /**
   * Retrieves the number of columns on the grid.
   * @return the number of columns.
   */
  int getCols();
}

