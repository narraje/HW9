package cs3500.threetrios.providers.model;

import java.util.Objects;

/**
 * This represents a 2D position of a grid cell, in either model, view, or intermediate
 * coordinates. The X field corresponds to the row index, while the Y field corresponds to
 * the column index. Row 0, column 0, is the top left corner, with increasing X values (rows)
 * heading down and increasing Y values (columns) heading to the right. Coordinates are always
 * non-negative integers (natural numbers).
 */
public class Posn {
  private final int x;
  private final int y;

  /**
   * Constructor for a Posn.
   * @param row the x-position of a point, or the row it corresponds to.
   * @param col the y-position of a point, or the column it corresponds to.
   */
  public Posn(int row, int col) {
    if (row < 0 || col < 0) {
      throw new IllegalArgumentException("Posn coordinates cannot be negative.");
    }
    this.x = row;
    this.y = col;
  }

  /**
   * Gets the integer representation of the row this Posn is representing.
   * @return the x-position of a point, or the row it corresponds to.
   */
  public int getX() {
    return this.x;
  }

  /**
   * Gets the integer representation of the column this Posn is representing.
   * @return the y-position of a point, or the column it corresponds to.
   */
  public int getY() {
    return this.y;
  }


  @Override
  public String toString() {
    return "(" + this.x + ", " + this.y + ")";

  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof Posn)) {
      return false;
    }
    Posn otherPosn = (Posn)other;
    return (this.getX() == otherPosn.getX() && this.getY() == otherPosn.getY());
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.x, this.y);
  }
}
