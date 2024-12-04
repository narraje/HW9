package cs3500.hw05.player;

import cs3500.hw05.model.ModelFeatures;
import cs3500.hw05.model.IModel;
import cs3500.hw05.model.grid.Posn;
import cs3500.hw05.view.gui.Features;
import cs3500.hw05.view.gui.handpanel.IHandPanel;
import cs3500.hw05.view.gui.IThreeTriosView;
import cs3500.hw05.view.gui.selection.SelectionManager;

/**
 * A controller for a player in the game Three Trios.
 * This class connects the model, view, and player, with the ability ot handle player actions.
 * Changes in the model are observed to refresh and update the view, and allows the player to select
 * cards and place them on the grid.
 */
public class PlayerController implements Features, ModelFeatures {
  protected final IModel model;
  protected final IThreeTriosView view;
  protected final IPlayer player;


  /**
   * Constructor for a PlayerController instance, with the ability to manage interactions
   * between the model,view, and a player.
   * @param model the model to interact with, which is responsible for enforcing rules and
   *              maintaining a game state.
   */
  public PlayerController(IModel model, IThreeTriosView view, IPlayer player) {
    this.model = model;
    this.view = view;
    this.player = player;
    model.addObserver(this);
    view.addObserver(this);
  }

  @Override
  public void selectCard(int cardIndex, IPlayer player) {
    try {
      if (!model.getCurrentPlayer().equals(this.player)) {
        return;
      }

      if (!player.equals(this.player)) {
        throw new IllegalArgumentException("Cannot select opponents card!");
      }

      IHandPanel playerHand = this.player.getPlayerType() == PlayerType.RED
          ? view.getLeftHandPanel()
          : view.getRightHandPanel();
      if (playerHand != null) {
        SelectionManager.getInstance().setSelectedCard(playerHand, cardIndex);
      }
    } catch (IllegalArgumentException e) {
      view.showError(e.getMessage());
    }
  }

  @Override
  public void placeCard(Posn position) {
    try {
      if (!model.getCurrentPlayer().equals(this.player)) {
        return;
      }
      var selectedCard = SelectionManager.getInstance().getSelectedCard();

      if (selectedCard == null) {
        throw new IllegalArgumentException("No card has been selected.");
      }

      String card = selectedCard.getName();

      System.out.println("Placing card: " + selectedCard + " at position: " + position);
      model.makeMove(card, position, model.getCurrentPlayer().getPlayerType());

      // Clear selection after placing the card
      IHandPanel playerHand = this.player.getPlayerType() == PlayerType.RED
          ? view.getLeftHandPanel()
          : view.getRightHandPanel();

      if (playerHand != null) {
        playerHand.clearSelection();
      }

      SelectionManager.getInstance().clearSelectedCard();

      view.refresh();
    } catch (IllegalArgumentException e) {
      view.showError(e.getMessage());
    }
  }



  @Override
  public void onTurn(IPlayer currentPlayer) {
    view.updateCurrentPlayerLabel(currentPlayer.getPlayerType());
    view.showTurnIndicator(currentPlayer);
    view.refresh();
  }

  @Override
  public void onGameOver(IPlayer winner) {
    view.refresh();
    winner = model.getWinner();

    int winnerScore = model.getPlayerScore(winner);

    view.showGameOver(winner, winnerScore);
  }
}
