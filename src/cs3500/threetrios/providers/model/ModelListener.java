package cs3500.threetrios.providers.model;

import cs3500.threetrios.providers.model.enums.PlayerColor;

/**
 * This interface represents a component that subscribes to any messages published by the model.
 */
public interface ModelListener {
  /**
   * Handle changes in the current Player's turn as described (published) by the model.
   * @param curPlayerColor identifier of the new Player whose turn it is
   */
  void handleChangeOfTurn(PlayerColor curPlayerColor);
}
