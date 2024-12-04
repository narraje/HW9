package cs3500.hw05.card;

import cs3500.hw05.model.grid.Posn;

/**
 * Represents the cardinal direction of card.
 */
public enum Direction {
  North, East, South, West;

  /**
   * Gets the opposite direction of a given direction.
   *
   * @return the opposite direction.
   */
  public Direction oppositeDirection() {
    switch (this) {
      case North:
        return Direction.South;
      case South:
        return Direction.North;
      case East:
        return Direction.West;
      case West:
        return Direction.East;
      default:
        throw new IllegalArgumentException("Invalid direction");
    }
  }

  /**
   * Determines the direction from one position to an adjacent position.
   *
   * @param from the starting position.
   * @param to   the adjacent position to determine the direction to.
   * @return the direction from 'from' to 'to'.
   * @throws IllegalArgumentException if the positions are not adjacent.
   */
  public static Direction getDirection(Posn from, Posn to) {
    int deltaX = to.getX() - from.getX(); // Vertical movement (rows)
    int deltaY = to.getY() - from.getY(); // Horizontal movement (columns)

    if (deltaX == -1 && deltaY == 0) {
      return Direction.North; // Moving up
    }
    if (deltaX == 1 && deltaY == 0) {
      return Direction.South; // Moving down
    }
    if (deltaX == 0 && deltaY == 1) {
      return Direction.East; // Moving right
    }
    if (deltaX == 0 && deltaY == -1) {
      return Direction.West; // Moving left
    }

    throw new IllegalArgumentException("Positions are not adjacent");
  }
}
