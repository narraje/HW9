package cs3500.threetrios.providers.player.strategies;

import java.util.Map;

import cs3500.threetrios.providers.model.Posn;
import cs3500.threetrios.providers.model.ReadOnlyModel;

/**
 * This interface represents a possible behavior of a strategic computer player of the
 * Three Trio game. If there are multiple "best" moves that can be chosen in a single strategy,
 * break ties by choosing the move with the uppermost-leftmost coordinate for the position and
 * then choose the best card for that position with an index closest to 0 in the hand.
 * If there are no valid moves, your player should pass choose the uppermost,
 * left-most open position and the card at index 0.
 */
public interface Strategy {
  /**
   * Choose the play that corresponds to this strategy. The model keeps track of the current player,
   * so only the current player can call this strategy.
   * @param model the representation of the Three Trios game
   * @return the grid coordinate and (Posn) and Card that this strategy elected to play to
   */
  Map<Posn, Integer> choosePlay(ReadOnlyModel model);
}
