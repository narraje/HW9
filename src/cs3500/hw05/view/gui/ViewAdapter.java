package cs3500.hw05.view.gui;

import cs3500.hw05.player.IPlayer;
import cs3500.hw05.player.PlayerController;
import cs3500.hw05.player.PlayerType;
import cs3500.hw05.view.gui.handpanel.IHandPanel;
import cs3500.threetrios.providers.view.SwingView;


public class ViewAdapter implements IThreeTriosView{


  public ViewAdapter(SwingView theirView) {
    theirView.makeVisible(true);
  }

  @Override
  public void refresh() {
  }

  @Override
  public void showTurnIndicator(IPlayer currentPlayer) {

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
}
