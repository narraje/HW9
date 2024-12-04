package cs3500.hw05.model.grid;

import cs3500.hw05.model.grid.cell.CardCell;
import cs3500.hw05.model.grid.cell.Hole;
import cs3500.hw05.model.grid.cell.ICell;
import cs3500.hw05.player.PlayerType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents the Grid for the game Three Trios, which cards can be placed on and interact with
 * each other.
 */
public class Grid implements IGrid {
  //The grid field is represented as a List of a List of ICells. The outer list represents the
  //rows (horizontal) of the grid, and the inner list represents the columns (vertical).
  //the rows and columns are 0-indexed.
  private List<List<ICell>> grid;

  /**
   * constructor to intialize layout.
   *
   * @param layout the 2d layout of the grid.
   */
  public Grid(char[][] layout) {
    initializeGrid(layout);
  }

  /**
   * deep copy constructor.
   *
   * @param other copies other grid.
   */
  public Grid(IGrid other) {
    if (other == null) {
      throw new IllegalArgumentException("Grid to copy cannot be null.");
    }
    int numRows = other.getRows();
    int numCols = other.getCols();
    this.grid = new ArrayList<>();
    for (int r = 0; r < numRows; r++) {
      List<ICell> newRow = new ArrayList<>();
      for (int c = 0; c < numCols; c++) {
        ICell originalCell = other.getCell(new Posn(r, c));
        newRow.add(originalCell.copy());
      }
      this.grid.add(newRow);
    }
  }


  // Method to initialize grid based on a layout
  @Override
  public void initializeGrid(char[][] layout) {
    if (layout == null || layout.length == 0 || layout[0].length == 0) {
      throw new IllegalArgumentException("Layout cannot be null or empty.");
    }

    int rows = layout.length;
    int cols = layout[0].length;

    // Clear any existing cells in the grid
    if (grid == null) {
      grid = new ArrayList<>();
    } else {
      grid.clear();
    }

    // Populate the grid based on layout
    for (int r = 0; r < rows; r++) {
      if (layout[r].length != cols) {
        throw new IllegalArgumentException("All rows must have the same number of columns.");
      }
      List<ICell> row = new ArrayList<>();
      for (int c = 0; c < cols; c++) {
        char cellSymbol = layout[r][c];
        switch (cellSymbol) {
          case 'C':
            row.add(new CardCell());
            break;
          case 'H':
            row.add(new Hole());
            break;
          default:
            throw new IllegalArgumentException("Invalid cell type at (" + r + ", " + c + "): "
                + cellSymbol);
        }
      }
      grid.add(row);
    }

    // Print layout for debugging
    System.out.println("Grid Layout:");
    for (List<ICell> row : grid) {
      for (ICell cell : row) {
        System.out.print(cell instanceof Hole ? "H " : "C ");
      }
      System.out.println();
    }
  }

  @Override
  public int cellCount() {
    int count = 0;
    for (List<ICell> row : grid) {
      for (ICell cell : row) {
        if (cell.isPlayable()) {
          count++;
        }
      }
    }
    return count;
  }

  /**
   * Counts the number of cards owned by a specific player on the grid.
   *
   * @param playerType the player whose cards should be counted.
   * @return the count of cards owned by the specified player.
   */
  @Override
  public int countPlayerCards(PlayerType playerType) {
    int count = 0;
    for (List<ICell> row : grid) {
      for (ICell cell : row) {
        if (cell.isPlayable() && cell.getCard() != null
            &&
            cell.getCard().getOwner() == playerType) {
          count++;
        }
      }
    }
    return count;
  }


  @Override
  public boolean isValidPosition(Posn posn) {
    if (!isWithinBounds(posn)) {
      System.out.println("Position " + posn + " is out of bounds.");
      return false;
    }

    ICell cell = getCell(posn);

    if (!cell.isPlayable()) {
      System.out.println("Cell at position " + posn + " is not playable.");
      return false;
    }


    if (cell.getCard() != null) {
      System.out.println("Cell at position " + posn + " is already occupied by "
          + cell.getCard().getName());
      return false;
    }

    return true;
  }


  @Override
  public List<Posn> getAdjacentPositions(Posn posn) {
    List<Posn> adjacentPositions = new ArrayList<>();

    int x = posn.getX(); // Row (vertical)
    int y = posn.getY(); // Column (horizontal)

    Posn north = new Posn(x - 1, y); // Up
    Posn south = new Posn(x + 1, y); // Down
    Posn east = new Posn(x, y + 1);  // Right
    Posn west = new Posn(x, y - 1);  // Left

    if (isWithinBounds(north)) {
      adjacentPositions.add(north);
    }
    if (isWithinBounds(south)) {
      adjacentPositions.add(south);
    }
    if (isWithinBounds(east)) {
      adjacentPositions.add(east);
    }
    if (isWithinBounds(west)) {
      adjacentPositions.add(west);
    }

    return adjacentPositions;
  }


  /**
   * Retrieves the cell at the specified position.
   *
   * @param posn the position of the cell to retrieve.
   * @return the ICell at the given position.
   * @throws IllegalArgumentException if the position is out of bounds.
   */
  @Override
  public ICell getCell(Posn posn) {
    int row = posn.getX(); // Row (vertical)
    int col = posn.getY(); // Column (horizontal)

    if (!isWithinBounds(posn)) {
      throw new IllegalArgumentException("Position is out of bounds.");
    }

    return grid.get(row).get(col);
  }


  public List<List<ICell>> getUnmodifiableGrid() {
    return Collections.unmodifiableList(grid.stream()
        .map(Collections::unmodifiableList)
        .collect(Collectors.toList()));
  }

  @Override
  public int getRows() {
    return grid.size();
  }

  @Override
  public int getCols() {
    return grid.isEmpty() ? 0 : grid.get(0).size();
  }

  /**
   * Checks if the specified position is within the bounds of the grid.
   *
   * @param posn the position to check.
   * @return true if the position is within bounds, false otherwise.
   */
  private boolean isWithinBounds(Posn posn) {
    int row = posn.getX(); // Row (vertical)
    int col = posn.getY(); // Column (horizontal)

    return row >= 0 && row < grid.size()
        &&
        col >= 0 && col < grid.get(row).size();
  }


  @Override
  public char[][] getLayout() {
    int rows = getRows();
    int cols = getCols();
    char[][] layout = new char[rows][cols];

    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        ICell cell = grid.get(r).get(c);
        layout[r][c] = cell instanceof Hole ? 'H' : 'C';
      }
    }
    return layout;
  }
}
