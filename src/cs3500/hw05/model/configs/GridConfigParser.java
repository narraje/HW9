package cs3500.hw05.model.configs;

import cs3500.hw05.model.grid.Grid;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * A class responsible for parsing a grid configuration from a file and creates a Grid.
 * Responsible for reading a file a converting those contents into a layout that is
 * playable.
 */

public class GridConfigParser {

  /**
   * Parses a grid configuration file and returns a Grid object.
   *
   * @param filePath the path to the grid configuration file.
   * @return a Grid object initialized based on the configuration file.
   * @throws FileNotFoundException if the file is not found.
   * @throws IllegalArgumentException if the file format is invalid.
   */
  public static Grid parseGridConfig(String filePath) throws FileNotFoundException {
    Scanner scanner = new Scanner(new File(filePath));

    // Read ROWS and COLS
    int rows = scanner.nextInt();
    int cols = scanner.nextInt();
    scanner.nextLine();

    char[][] layout = new char[rows][cols];

    for (int r = 0; r < rows; r++) {
      if (!scanner.hasNextLine()) {
        throw new IllegalArgumentException("Not enough rows in grid configuration.");
      }
      String line = scanner.nextLine().trim();
      if (line.length() != cols) {
        throw new IllegalArgumentException("Row " + r + " does not have " + cols + " columns.");
      }
      for (int c = 0; c < cols; c++) {
        char cellChar = line.charAt(c);
        if (cellChar != 'C' && cellChar != 'X') {
          throw new IllegalArgumentException("Invalid cell character at (" + r + ", " + c + "): "
              + cellChar);
        }
        layout[r][c] = cellChar == 'X' ? 'H' : 'C';
      }
    }



    return new Grid(layout);
  }
}