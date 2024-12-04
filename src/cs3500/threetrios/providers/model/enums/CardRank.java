package cs3500.threetrios.providers.model.enums;

/**
 * This enumerated type represents all the possible score values for a Card in this game.
 */
public enum CardRank {
  ONE('1', 1), TWO('2', 2), THREE('3', 3), FOUR('4', 4),
  FIVE('5', 5), SIX('6', 6), SEVEN('7', 7), EIGHT('8', 8),
  NINE('9', 9), TEN('A', 10);

  private final char val;
  private final int num;

  /**
   * Constructor for the CardRank Enumerator.
   *
   * @param val the character value of this CardRank.
   * @param i the numerical value of this CardRank.
   */
  CardRank(char val, int i) {
    this.val = val;
    num = i;
  }

  @Override
  public String toString() {
    return String.valueOf(this.val);
  }

  public int getNum() {
    return this.num;
  }

  /** Determine if this CardRank value is greater than the given Enum's value.
   *
   * @param other the other CardRank.
   * @return true if this CardRank has a greater value than the given CardRank.
   */
  public boolean isGreaterThan(CardRank other) {
    return ((int) this.val > (int) other.val);
  }

  /**
   * Return a CardRank representing the given character.
   * @param character the character representation of this CardRank
   * @return a CardRank representation of the given character
   */
  public static CardRank fromChar(char character) {
    for (CardRank cr : CardRank.values()) {
      if (cr.val == character) {
        return cr;
      }
    }
    throw new IllegalArgumentException("No CardRank found for character: " + character);
  }
}
