package cs3500.hw05.strategy;

import cs3500.hw05.card.ICard;
import cs3500.hw05.model.ReadonlyThreeTriosModel;
import cs3500.hw05.model.grid.Posn;
import cs3500.hw05.player.PlayerType;
import java.util.List;

/**
 * A hybrid strategy that switches between different strategies depending on the game context,
 * picking the one that would work best in a certain situation.
 */
public class HybridStrategy implements Strategy {
  private final List<Strategy> strategies;

  /**
   * Constructor for the HybridStrategy class.
   *
   * @param strategies the strategies that the class will consider when making a move.
   */
  public HybridStrategy(List<Strategy> strategies) {
    this.strategies = strategies;
  }

  @Override
  public ICard chooseCard(ReadonlyThreeTriosModel model, PlayerType playerType) {
    for (Strategy strategy : strategies) {
      ICard card = strategy.chooseCard(model, playerType);
      if (card != null) {
        return card;
      }
    }
    throw new IllegalStateException("No valid card choice found in any strategy.");
  }

  @Override
  public Posn choosePosition(ReadonlyThreeTriosModel model, PlayerType playerType, ICard card) {
    for (Strategy strategy : strategies) {
      Posn position = strategy.choosePosition(model, playerType, card);
      if (position != null) {
        return position;
      }
    }
    throw new IllegalStateException("No valid position found in any strategy.");
  }
}

