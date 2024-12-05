package cs3500.hw05.view.gui;

import cs3500.hw05.model.grid.Posn;
import cs3500.hw05.player.IPlayer;
import cs3500.hw05.player.PlayerType;
import cs3500.threetrios.providers.controller.ClickListener;
import cs3500.threetrios.providers.model.enums.PlayerColor;

public class FeaturesAdapter implements Features {
  private ClickListener theirFeatures;

  public FeaturesAdapter(ClickListener theirFeatures) {
    this.theirFeatures = theirFeatures;
  }

  @Override
  public void selectCard(int cardIndex, IPlayer player) {
    PlayerColor color = player.getPlayerType() == PlayerType.RED
        ? PlayerColor.RED
        : PlayerColor.BLUE;
    theirFeatures.handleHandClick(color, cardIndex);
  }

  @Override
  public void placeCard(Posn position) {
    theirFeatures.handleGridClick(new cs3500.threetrios.providers.model.Posn(position.getX(), position.getY()));
  }
}
