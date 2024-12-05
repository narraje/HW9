package cs3500.hw05.view.gui;

import cs3500.hw05.model.grid.Posn;
import cs3500.hw05.player.IPlayer;
import cs3500.hw05.player.PlayerController;
import cs3500.hw05.player.PlayerType;
import cs3500.hw05.view.gui.gridpanel.GridPanelListener;
import cs3500.hw05.view.gui.handpanel.HandPanelListener;
import cs3500.hw05.view.gui.handpanel.IHandPanel;
import cs3500.threetrios.providers.view.SwingView;

public class ViewAdapter implements IThreeTriosView, HandPanelListener, GridPanelListener {
  private final SwingView theirView;

  public ViewAdapter(SwingView theirView) {
    this.theirView = theirView;
    theirView.makeVisible(true);
  }

  @Override
  public void refresh() {
    theirView.refresh();
  }

  @Override
  public void showTurnIndicator(IPlayer currentPlayer) {
    theirView.refresh(true);
  }

  @Override
  public void showGameOver(IPlayer winner, int winnerScore) {

  }

  @Override
  public IHandPanel getRightHandPanel() {
    return null;
  }

  @Override
  public IHandPanel getLeftHandPanel() {
    return null;
  }

  @Override
  public void addObserver(PlayerController observer) {

  }

  @Override
  public void updateCurrentPlayerLabel(PlayerType currentPlayer) {

  }

  @Override
  public void showError(String errorMessage) {

  }

  @Override
  public void onPlaceCard(Posn pos) {

  }

  @Override
  public void onCardSelected(int cardIndex, IPlayer player) {

  }
}
