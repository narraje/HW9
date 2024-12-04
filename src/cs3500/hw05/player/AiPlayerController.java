package cs3500.hw05.player;

import cs3500.hw05.card.ICard;
import cs3500.hw05.model.IModel;
import cs3500.hw05.model.grid.Posn;
import cs3500.hw05.strategy.Strategy;
import cs3500.hw05.view.gui.IThreeTriosView;

/**
 * A specialized controller for an AI player in the game Three Trios.
 * Extends the traditional PlayerController to handle AI-specific behaviors.
 */
public class AiPlayerController extends PlayerController {
  private final Strategy strategy;

  /**
   * Constructor for an AiPlayerController instance.
   *
   * @param model    the game model to interact with.
   * @param view     the view associated with this AI player.
   * @param player   the AI player this controller manages.
   * @param strategy the strategy used by the AI to make moves.
   */
  public AiPlayerController(IModel model, IThreeTriosView view, IPlayer player, Strategy strategy) {
    super(model, view, player);
    this.strategy = strategy;
  }

  @Override
  public void onTurn(IPlayer currentPlayer) {
    // Check if it's the AI player's turn
    if (!currentPlayer.equals(this.player)) {
      return;
    }

    System.out.println("AI player's turn.");
    try {
      // Use the strategy to choose a card and a position
      ICard chosenCard = strategy.chooseCard(model, player.getPlayerType());
      Posn chosenPos = strategy.choosePosition(model, player.getPlayerType(), chosenCard);

      // Make the move
      model.makeMove(chosenCard.getName(), chosenPos, currentPlayer.getPlayerType());
      System.out.println("AI placed card: " + chosenCard.getName() + " at " + chosenPos);

    } catch (Exception e) {
      System.err.println("AI move failed: " + e.getMessage());
    }

    view.refresh();
  }
}

