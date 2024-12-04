package cs3500.threetrios.providers.model.enums;


import java.awt.Color;

/**
 * Represents the color of a Player in a ThreeTrio game.
 * There are currently only two players supported,
 * which are represented by the colors Red and Blue.
 */
public enum PlayerColor {
  RED("RED", Color.RED), BLUE("BLUE", Color.BLUE);

  private final String colorName;
  private final Color color;


  /**
   * Constructor for a PlayerColor.
   * @param colorName the String representation of this PlayerColor.
   */
  PlayerColor(String colorName, Color color) {
    this.colorName = colorName;
    this.color = color;

  }

  /**
   * Determines the graphics-usable implementation of this Player Color's color.
   * @return the java.awt.Color representation of this PlayerColor.
   */
  public Color toRealColor() {
    return this.color;
  }

  /**
   * Converts this PlayerColor to its String representation.
   * @return a String that represents this PlayerColor's name.
   */
  public String toString() {
    return this.colorName;
  }
}
