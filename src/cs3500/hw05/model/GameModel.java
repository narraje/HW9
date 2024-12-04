package cs3500.hw05.model;

import cs3500.hw05.card.Card;
import cs3500.hw05.card.Direction;
import cs3500.hw05.card.ICard;
import cs3500.hw05.model.grid.IGrid;
import cs3500.hw05.model.grid.cell.CardCell;
import cs3500.hw05.player.IPlayer;
import cs3500.hw05.player.PlayerController;
import cs3500.hw05.player.PlayerType;
import cs3500.hw05.model.grid.Grid;
import cs3500.hw05.model.grid.cell.ICell;
import cs3500.hw05.model.grid.Posn;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents the class for the Model of the game Three Trios, which manages
 * the logic for the grid, cards, and players of the game.
 * Responsible for starting the game, processing moves, and determining when the game ends,
 * and if there is a winner.
 *
 *<p></p>
 * CLASS INVARIANCE : The game must be started in order to make any moves throughout the game.
 * This is enforced by throwing Exceptions if methods such as makeMove, isGameOver, or getWinner are
 * called without ever starting the game.
 */
public class GameModel implements IModel {

  private final IGrid grid;
  private final List<ICard> deck;
  private IPlayer currentPlayer;
  private final IPlayer player1;
  private final IPlayer player2;
  private boolean gameStarted;
  private int filledCellCount = 0;
  private final List<PlayerController> observers = new ArrayList<>();

  /**
   * Constructs a new GameModelImpl with the provided grid and deck of cards.
   *
   * @param grid the game grid.
   * @param deck the deck of cards to be dealt to players.
   * @param layout the layout of the grid with holes and card cells
   * @param player1 the first player (could be human or AI).
   * @param player2 the second player (could be human or AI).
   * @throws IllegalArgumentException if there are not enough cards for the grid size.
   */
  public GameModel(IGrid grid, List<ICard> deck, char[][] layout, IPlayer player1,
      IPlayer player2) {
    if (grid == null || deck == null || player1 == null || player2 == null) {
      throw new IllegalArgumentException("Grid and deck cannot be null.");
    }

    this.grid = new Grid(grid);
    this.deck = new ArrayList<>();
    for (ICard card : deck) {
      this.deck.add(card.copy());
    }

    this.player1 = player1;
    this.player2 = player2;
    this.currentPlayer = player1;
    this.gameStarted = false;

    Set<String> uniqueCards = new HashSet<>();
    for (ICard card : deck) {
      if (!uniqueCards.add(card.getName())) {
        throw new IllegalArgumentException("Deck contains duplicate cards: " + card.getName());
      }
    }
  }

  /**
   * Distributes cards to each player's hand based on the specified hand size.
   *
   * @param handSize the number of cards to deal to each player.
   */
  private void dealCards(int handSize, IPlayer player) {
    List<ICard> playerHand = new ArrayList<>();
    for (int i = 0; i < handSize; i++) {
      ICard card = deck.remove(0);
      card.setOwner(player.getPlayerType());
      playerHand.add(card);
    }
    player.setHand(playerHand);
  }


  /**
   * Initializes the game by dealing cards to each player and setting the game state as started.
   * Validates the specified hand size and ensures there are enough cards in the deck to
   * proceed. Optionally shuffles the deck before dealing.
   *
   * @param shuffle whether to shuffle the deck before dealing cards
   * @param handSize the number of cards to deal to each player
   * @throws IllegalStateException if the game has already started
   * @throws IllegalArgumentException if the specified hand size does not match the required
   *                                  hand size based on the grid layout or if there are not
   *                                  enough cards in the deck for both players
   */
  @Override
  public void startGame(boolean shuffle, int handSize) {
    if (gameStarted) {
      throw new IllegalStateException("Game cannot have already started.");
    }

    if (shuffle) {
      Collections.shuffle(deck);
    }

    int expectedHandSize = (grid.cellCount() + 1) / 2;
    if (handSize != expectedHandSize) {
      throw new IllegalArgumentException("Hand size must be equal to " + expectedHandSize);
    }

    int requiredCards = handSize * 2;
    if (deck.size() < requiredCards) {
      throw new IllegalArgumentException("Not enough cards for the specified hand size.");
    }
    dealCards(handSize, player1);
    dealCards(handSize, player2);
    gameStarted = true;
    notifyControllerOfTurn();
  }


  /**
   * Executes a move by placing a specified card at a given position on the grid.
   * Validates the move based on the game state and the current player's turn,
   * and initiates a battle phase to resolve any card flips due to the move.
   * Updates the game state and checks if the game has ended after the move.
   *
   * @param cardName the name of the card to place
   * @param pos the position on the grid where the card is to be placed
   * @param playerType the player making the move
   * @throws IllegalStateException if the game has not started
   * @throws IllegalArgumentException if it is not the specified player's turn,
   *                                  if the position is invalid or already occupied,
   *                                  or if the specified card is not in the player's hand
   */
  @Override
  public void makeMove(String cardName, Posn pos, PlayerType playerType) {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started.");
    }

    if (currentPlayer.getPlayerType() != playerType) {
      throw new IllegalArgumentException("It's not " + playerType + "'s turn.");
    }

    if (!grid.isValidPosition(pos)) {
      throw new IllegalArgumentException("Invalid move: cell is not playable or is occupied.");
    }

    ICard card = currentPlayer.getCardByName(cardName);
    if (card == null) {
      throw new IllegalArgumentException("This is not your card: " + cardName);
    }
    placeCard(card, pos);
    currentPlayer.removeCard(card);

    executeBattlePhase(pos);

    if (isGameOver()) {
      notifyControllerOfGameOver();
      gameStarted = false;
    } else {
      switchTurns();
      notifyControllerOfTurn();
    }
  }

  /**
   * a private helper method that will switch a player's turn by changing the PlayerType field in
   * the model class.
   */
  private void switchTurns() {
    currentPlayer = (currentPlayer == player1) ? player2 : player1;
  }

  /**
   * game is over if all cells are filled.
   */
  @Override
  public boolean isGameOver() {
    return filledCellCount == grid.cellCount();
  }

  /**
   * determines the winner of the game based on amount of cards on grid+hand.
   * @return PlayerType identifies winner.
   */
  @Override
  public IPlayer getWinner() {
    if (!isGameOver()) {
      throw new IllegalStateException("Game has to be over");
    }

    if (getPlayerScore(player1) > getPlayerScore(player2)) {
      return player1;
    } else {
      return player2;
    }
  }

  /**
   * gets current player.
   *
   * @return the current player of the game.
   */
  @Override
  public IPlayer getCurrentPlayer() {
    return currentPlayer;
  }

  /**
   * gets player1.
   *
   * @return player1.
   */
  @Override
  public IPlayer getPlayer1() {
    return player1;
  }

  /**
   * gets player2.
   *
   * @return player2.
   */
  @Override
  public IPlayer getPlayer2() {
    return player2;
  }

  /**
   * gets the cell.
   *
   * @param posn is the position of the cell to retrieve.
   *
   * @return cell based on posn.
   */
  @Override
  public ICell getCell(Posn posn) {
    return grid.getCell(posn);
  }

  /**
   * gets the rows.
   *
   * @return the amount of rows in the grid.
   */
  @Override
  public int getRows() {
    return grid.getRows();
  }

  /**
   * gets the cols.
   *
   * @return the amount of columns in the grid.
   */
  @Override
  public int getCols() {
    return grid.getCols();
  }

  /**
   * gets the grid.
   *
   * @return a deep copy of grid.
   */
  @Override
  public Grid getGrid() {
    return new Grid(this.grid);
  }

  /**
   * Places a specified card at a given position on the grid. Ensures that the game has started
   * and that the position is valid and unoccupied before placing the card.
   *
   * @param card the card to place on the grid
   * @param posn the position on the grid where the card should be placed
   * @throws IllegalStateException if the game has not started
   * @throws IllegalArgumentException if the specified position is invalid or already occupied
   */
  private void placeCard(ICard card, Posn posn) {
    // Check that the game has started
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started.");
    }

    // Validate the position using the Grid's method
    if (!grid.isValidPosition(posn)) {
      throw new IllegalArgumentException(
          "Cannot place card: Position is invalid or already occupied.");
    }


    Card cardToPlace = card.copy();
    ICell cell = grid.getCell(posn);
    if (!(cell instanceof CardCell)) {
      throw new IllegalStateException("Cannot place a card in a non-playable cell (Hole) at "
          + "position " + posn);
    }
    cell.setCard(cardToPlace);
    filledCellCount++;
  }


  /**
   * Executes the battle phase after a card is placed.
   *
   * @param position the position of the newly placed card.
   */
  private void executeBattlePhase(Posn position) {
    // Initialize the set of visited positions
    Set<Posn> visitedPositions = new HashSet<>();
    // Use a stack to manage positions to process
    Deque<Posn> stack = new ArrayDeque<>();
    stack.push(position);

    while (!stack.isEmpty()) {
      Posn currentPosition = stack.pop();

      if (visitedPositions.contains(currentPosition)) {
        continue; // Skip if we've already visited this position
      }
      visitedPositions.add(currentPosition);

      // Get the card at the current position
      ICard placedCard = grid.getCell(currentPosition).getCard();
      PlayerType playerType = placedCard.getOwner();

      // Check each adjacent cell
      for (Posn adjacentPos : grid.getAdjacentPositions(currentPosition)) {
        ICell adjacentCell = grid.getCell(adjacentPos);
        ICard adjacentCard = adjacentCell.getCard();

        if (adjacentCard != null && adjacentCard.getOwner() != playerType) {
          Direction directionToAdjacent = Direction.getDirection(currentPosition, adjacentPos);
          Direction oppositeDirection = directionToAdjacent.oppositeDirection();

          int attackValue = placedCard.getAttackValue(directionToAdjacent);
          System.out.println("Placed cards attack value: " + attackValue);
          int defenseValue = adjacentCard.getAttackValue(oppositeDirection);
          System.out.println("adjacent cards attack value: " + defenseValue);

          if (attackValue > defenseValue) {
            adjacentCell.flipCardOwner();
            System.out.println("Adjacent card flipped");
            stack.push(adjacentPos);
          }
        }
      }
    }
  }

  /**
   * Gets the score of the specified player.
   *
   * @param player the player whose score to retrieve.
   * @return the player's score.
   */
  @Override
  public int getPlayerScore(IPlayer player) {
    int playerHandSize = player.getHand().size();
    int playerGridCards = grid.countPlayerCards(player.getPlayerType());

    return playerHandSize + playerGridCards;
  }

  @Override
  public List<ICard> getDeck() {
    return this.deck;
  }

  /**
   * Gets the hand of the player.
   *
   * @param player the player whose hand to retrieve.
   * @return players hand.
   */
  @Override
  public List<ICard> getPlayerHand(IPlayer player) {
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null.");
    }
    return player.getHand();
  }

  @Override
  public void addObserver(PlayerController obs) {
    observers.add(obs);
  }

  /**
   * notifies the controller that a turn was observed.
   */
  private void notifyControllerOfTurn() {
    for (PlayerController obs : observers) {
      obs.onTurn(currentPlayer);
    }
  }

  /**
   * notifies the controller that a game over state was observed.
   */
  private void notifyControllerOfGameOver() {
    for (PlayerController obs : observers) {
      obs.onGameOver(getWinner());
    }
  }
}
