package cs3500.hw05.card;

import cs3500.threetrios.providers.model.Card;
import cs3500.threetrios.providers.model.enums.CardRank;
import cs3500.threetrios.providers.model.enums.Direction;
import cs3500.threetrios.providers.model.enums.PlayerColor;

/**
 * A class to adapt the provider's Card interface. Their methods are adapted to work with our
 * implementations.
 */
public class CardAdapter implements Card {
  private final ICard card;

  /**
   * Constructor for the CardAdapter class. Initializes an ICard that comes from our original
   * implementation.
   * @param card the ICard to be used.
   */
  public CardAdapter(ICard card) {
    this.card = card;
  }

  @Override
  public boolean isGreaterThan(Card other, Direction dir) {
    return false;
    //no uses
  }

  /**
   * Used in getRank which is utilized in the view.
   *
   * @param providerDirection idk.
   * @return gyattemheimer.
   */
  private cs3500.hw05.card.Direction mapDirection(cs3500.threetrios.providers.model.enums.Direction providerDirection) {
    switch (providerDirection) {
      case NORTH:
        return cs3500.hw05.card.Direction.North;
      case SOUTH:
        return cs3500.hw05.card.Direction.South;
      case EAST:
        return cs3500.hw05.card.Direction.East;
      case WEST:
        return cs3500.hw05.card.Direction.West;
      default:
        throw new IllegalArgumentException("Unknown direction: " + providerDirection);
    }
  }


  @Override
  public CardRank getRank(Direction dir) {
    int attackValue = card.getAttackValue(mapDirection(dir));
    return mapAttackValueToRank(attackValue);
  }

  private CardRank mapAttackValueToRank(int attackValue) {
    switch (attackValue) {
      case 1:
        return cs3500.threetrios.providers.model.enums.CardRank.ONE;
      case 2:
        return cs3500.threetrios.providers.model.enums.CardRank.TWO;
      case 3:
        return cs3500.threetrios.providers.model.enums.CardRank.THREE;
      case 4:
        return cs3500.threetrios.providers.model.enums.CardRank.FOUR;
      case 5:
        return cs3500.threetrios.providers.model.enums.CardRank.FIVE;
      case 6:
        return cs3500.threetrios.providers.model.enums.CardRank.SIX;
      case 7:
        return cs3500.threetrios.providers.model.enums.CardRank.SEVEN;
      case 8:
        return cs3500.threetrios.providers.model.enums.CardRank.EIGHT;
      case 9:
        return cs3500.threetrios.providers.model.enums.CardRank.NINE;
      case 10:
        return cs3500.threetrios.providers.model.enums.CardRank.TEN;
      default:
        throw new IllegalArgumentException("Invalid attack value: " + attackValue);
    }
  }

  @Override
  public PlayerColor getColor() {
    switch (card.getOwner()) {
      case RED:
        return PlayerColor.RED;
      case BLUE:
        return PlayerColor.BLUE;
      default:
        throw new IllegalArgumentException("Unknown color: " + card.getOwner());
    }
  }

  @Override
  public void setOwner(PlayerColor player) {
    //not used
  }
}
