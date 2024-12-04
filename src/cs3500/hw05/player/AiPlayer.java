package cs3500.hw05.player;

import cs3500.hw05.card.ICard;
import cs3500.hw05.model.IModel;
import cs3500.hw05.model.grid.Posn;
import cs3500.hw05.strategy.Strategy;
import java.util.List;

/**
 * Represents an AI player which makes moves based on strategies without human decisions or inputs.
 */
public class AiPlayer extends AbstractPlayer {
  private final Strategy strategy;

  /**
   * Constructor for an AI player.
   *
   * @param playerType AI's player type (RED or BLUE).
   * @param strategy AI's implemented strategy for playing.
   * @param initialHand AI's initial hand.
   */
  public AiPlayer(PlayerType playerType, Strategy strategy, List<ICard> initialHand) {
    super(playerType, initialHand);
    this.strategy = strategy;
  }

  /**
   * PlayTurn automates the move made by the AI with the chosen strategy and position.
   *
   * @param model the current state of the model.
   */
  public void playTurn(IModel model) {
    if (hand.isEmpty()) {
      System.out.println("AI has no cards to play.");
      return;
    }

    ICard chosenCard = strategy.chooseCard(model, this.playerType);

    if (chosenCard == null) {
      System.out.println("AI could not choose a card.");
      return;
    }

    Posn chosenPosition = strategy.choosePosition(model, this.playerType, chosenCard);

    if (chosenPosition == null) {
      System.out.println("AI could not determine a valid position.");
      return;
    }

    try {
      model.makeMove(chosenCard.getName(), chosenPosition, this.playerType);
      removeCard(chosenCard);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }
}

