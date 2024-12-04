package cs3500.hw05.view.gui.gridpanel;

import cs3500.hw05.model.ReadonlyThreeTriosModel;

/**
 * Defines the behaviors required for the grid panel in the view for the game Three Trios.
 * Displays the main game grid, allows user interactions with individual cells, \
 * and manages visual highlights on the grid.
 */
public interface IGridPanel {

  /**
   * Initializes the grid panel with the specified grid data. Sets up the grid layout and populates
   * it based on the given model, allowing the view to accurately represent the game state.
   */
  void initializeGrid(ReadonlyThreeTriosModel model);
}
