package cs3500.threetrios.providers.model.enums;

/**
 * Represents the Direction of a specific CardRank on certain Card.
 */
public enum Direction {
  NORTH, SOUTH, EAST, WEST;

  /**
   * This method is used for comparing CardRanks of adjacent Cards.
   * @return the Direction that faces opposite to this one.
   */
  public Direction getOpposite() {
    switch (this) {
      case NORTH:
        return SOUTH;
      case EAST:
        return WEST;
      case SOUTH:
        return NORTH;
      default:
        return EAST;
    }
  }
}
