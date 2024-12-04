package cs3500.hw05.model.grid;


/**
 * A class to handle positions.
 */

public class Posn {
  private final int x;
  private final int y;

  /**
   * Constructs a Posn with specified x and y coordinates.
   * @param x the x-coordinate (column index).
   * @param y the y-coordinate (row index).
   */
  public Posn(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Gets the x-coordinate.
   * @return the x-coordinate.
   */
  public int getX() {
    return x;
  }

  /**
   * Gets the y-coordinate.
   * @return the y-coordinate.
   */
  public int getY() {
    return y;
  }

  /**
   * Checks if this position is equal to another position.
   * @param obj the object to compare with.
   * @return true if the positions have the same x and y values; false otherwise.
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Posn)) {
      return false;
    }
    Posn other = (Posn) obj;
    return this.x == other.x && this.y == other.y;
  }

  /**
   * Computes a hash code for this position.
   * @return the hash code.
   */
  @Override
  public int hashCode() {
    return 31 * x + y;
  }

  @Override
  public String toString() {
    return "(" + this.x + ", " + this.y + ")";
  }
}

