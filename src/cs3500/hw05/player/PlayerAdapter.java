package cs3500.hw05.player;

import java.util.Objects;
import cs3500.threetrios.providers.controller.ClickListener;
import cs3500.threetrios.providers.model.enums.PlayerColor;
import cs3500.threetrios.providers.player.Player;

/**
 * An adapter for the provider's version of a Player in Three Trios. This will be written to work
 * with our implementation of an IPlayer.
 */
public class PlayerAdapter implements Player {
  private final IPlayer player;


  /**
   * Constructor for our PlayerAdapter implementation.
   * @param player the player that is being adapted - cannot be null.
   */
  public PlayerAdapter(IPlayer player) {
    this.player = Objects.requireNonNull(player);
  }

  @Override
  public PlayerColor getColor() {
    return player.getPlayerType() == PlayerType.RED
            ? PlayerColor.RED
            : PlayerColor.BLUE;
  }

  /**
   * We do not need to adapt this becuase it is negligent to our
   * design due to how we implemented an AI player.
   */
  @Override
  public void playMove() {
  }

  /**
   * Also not relevant to the design question at hand. Listening of
   * clicking events is handled in the view and related to the controller.
   */
  @Override
  public void addClickListener(ClickListener listener) {
  }
}
