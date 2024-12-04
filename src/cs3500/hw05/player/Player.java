package cs3500.hw05.player;

import cs3500.hw05.card.ICard;
import java.util.List;

/**
 * Represents a traditional red/blue Player in the game Three Trios. This player is controlled by
 * a human player, who will manually select and place cards during the game.
 */
public class Player extends AbstractPlayer {
  /**
   * represents a player in the three trios game.
   *
   * @param playerType the type of the player.
   * @param initialHand the hand.
   */
  public Player(PlayerType playerType, List<ICard> initialHand) {
    super(playerType, initialHand);
  }

  @Override
  public String toString() {
    return "Player{"
        +
        "type=" + getPlayerType()
        +
        "}";
  }
}
