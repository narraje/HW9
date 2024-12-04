package cs3500.threetrios.providers.controller;

/**
 * Represents a Controller for ThreeTrios: handle user moves by executing them using the model;
 * convey move outcomes to the user in some form.
 */
public interface Controller {
  /**
   * Execute a single game of ThreeTrios given a ThreeTrios Model. When the game is over,
   * the playGame method ends.
   * @param shuffle whether to shuffle the deck or not
   * @param numPlayers the number of players playing the game
   * @param gridConfigFilepath path to the grid configuration file
   * @param cardsConfigFilepath path to the cards configuration file
   * @throws IllegalArgumentException if one of the given file paths is not found
   */
  void playGame(boolean shuffle, int numPlayers,
                String gridConfigFilepath, String cardsConfigFilepath);

  /**
   * Add this Controller's Player to this Controller's Model.
   */
  void addPlayerToModel();
}
