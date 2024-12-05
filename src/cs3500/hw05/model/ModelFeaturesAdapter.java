package cs3500.hw05.model;

import cs3500.hw05.player.IPlayer;
import cs3500.hw05.player.PlayerType;
import cs3500.threetrios.providers.model.ModelListener;
import cs3500.threetrios.providers.model.enums.PlayerColor;

public class ModelFeaturesAdapter implements ModelFeatures {
  private final ModelListener theirModelFeatures;

  public ModelFeaturesAdapter(ModelListener theirModelFeatures) {
    this.theirModelFeatures = theirModelFeatures;
  }

  @Override
  public void onTurn(IPlayer currentPlayer) {
    PlayerColor color = currentPlayer.getPlayerType() == PlayerType.RED
        ? PlayerColor.RED
        : PlayerColor.BLUE;
    theirModelFeatures.handleChangeOfTurn(color);
  }

  @Override
  public void onGameOver(IPlayer winner) {
    //not implemented
  }
}
