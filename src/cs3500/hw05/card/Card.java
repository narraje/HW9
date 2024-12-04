package cs3500.hw05.card;

import cs3500.hw05.player.PlayerType;
import java.util.EnumMap;
import java.util.Objects;

/**
 * represents a card of the game of Three Trios, which contain their own unique name, an owner, and
 * their own set of attack values for each cardinal direction of the card.
 */
public class Card implements ICard {

  private final String cardName;
  //The attackValue is represented with an EnumMap, where the key is an enum Direction, and the
  //value is an enum CardNumber.
  private final EnumMap<Direction, CardNumber> attackValue;
  private PlayerType owner;


  /**
   * Creates a Card for the game Three Trios.
   *
   * @param name        is a String name of the card.
   * @param attackValue is an EnumMap which holds attack values for all directions of the card.
   * @throws IllegalArgumentException if name, attackValue, OR owner is null.
   */
  public Card(String name, EnumMap<Direction, CardNumber> attackValue) {
    if (name == null || attackValue == null) {
      throw new IllegalArgumentException("Argument cannot be null.");
    }

    if (attackValue.size() != Direction.values().length) {
      throw new IllegalArgumentException("Attack values must include all directions");
    }

    for (Direction dir : Direction.values()) {
      boolean nullDir = attackValue.get(dir) == null;
      if (!attackValue.containsKey(dir) || nullDir) {
        throw new IllegalArgumentException("Attack values must include all directions with valid"
            + "card numbers");
      }
    }

    this.attackValue = new EnumMap<>(Direction.class);
    for (Direction dir : Direction.values()) {
      this.attackValue.put(dir, attackValue.get(dir));
    }

    this.cardName = name;
    this.owner = null;
  }

  @Override
  public String getName() {
    return cardName;
  }

  @Override
  public PlayerType getOwner() {
    return owner;
  }

  @Override
  public void setOwner(PlayerType owner) {
    this.owner = owner;
  }

  @Override
  public Card copy() {
    Card copiedCard = new Card(this.cardName, this.attackValue);
    copiedCard.setOwner(this.owner);
    return copiedCard;
  }

  @Override
  public int getAttackValue(Direction direction) {
    if (direction == null) {
      throw new IllegalArgumentException("direction cannot be null.");
    }
    return this.attackValue.get(direction).getValue();
  }

  public EnumMap<Direction, CardNumber> getAttackValues() {
    return new EnumMap<>(this.attackValue);
  }

  /**
   * Enumeration for the Number on the card, contains a string abbreviation as well as an integer
   * value from 1 to 10, inclusive. The number 10 is represented with the abbreviation "A".
   */
  public enum CardNumber {
    ONE("1", 1),
    TWO("2", 2),
    THREE("3", 3),
    FOUR("4", 4),
    FIVE("5", 5),
    SIX("6", 6),
    SEVEN("7", 7),
    EIGHT("8", 8),
    NINE("9", 9),
    TEN("A", 10);

    private final String abbreviation;
    private final int value;

    CardNumber(String abbreviation, int value) {
      this.abbreviation = abbreviation;
      this.value = value;
    }

    /**
     * Gets the abbreviation of the card number.
     *
     * @return the abbreviation as a String.
     */
    public String getAbbreviation() {
      return abbreviation;
    }

    /**
     * Gets the integer value of the card number.
     *
     * @return the value as an int.
     */
    public int getValue() {
      return this.value;
    }

    /**
     * gets cardnumber value of a card for parsing in databse.
     *
     * @param value is value of cardnumber
     * @return CardNumber for use in databse.
     */
    public static CardNumber getByValue(int value) {
      for (CardNumber cn : CardNumber.values()) {
        if (cn.getValue() == value) {
          return cn;
        }
      }
      throw new IllegalArgumentException("Invalid CardNumber value: " + value);
    }
  }

  @Override
  public String getAttackAbbreviation(Direction direction) {
    return attackValue.get(direction).getAbbreviation();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Card)) {
      return false;
    }
    Card other = (Card) obj;
    return this.cardName.equals(other.cardName)
        && this.attackValue.equals(other.attackValue);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cardName, attackValue);
  }

  @Override
  public String toString() {
    return cardName;
  }
}
