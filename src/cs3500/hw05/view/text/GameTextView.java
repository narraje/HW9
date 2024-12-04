package cs3500.hw05.view.text;

import cs3500.hw05.card.ICard;
import cs3500.hw05.card.Direction;
import cs3500.hw05.model.ReadonlyThreeTriosModel;
import cs3500.hw05.player.PlayerType;
import cs3500.hw05.model.grid.cell.ICell;
import cs3500.hw05.model.grid.Posn;
import java.io.IOException;
import java.util.List;

/**
 * Represents the textual view for the game Three Trios.
 */
public class GameTextView {

  private final Appendable out;
  private final ReadonlyThreeTriosModel model;

  /**
   * Constructs a text-based view for the game.
   *
   * @param model the game model to represent
   * @param out   the destination for output
   * @throws IllegalArgumentException if model or out is null
   */
  public GameTextView(ReadonlyThreeTriosModel model, Appendable out) {
    if (model == null || out == null) {
      throw new IllegalArgumentException("Model and output cannot be null.");
    }
    this.model = model;
    this.out = out;
  }

  /**
   * Renders the current game state to the provided Appendable output.
   *
   * @throws IOException if an error occurs when appending
   */
  public void render() throws IOException {
    out.append(this.toString());
  }

  /**
   * Builds a string representation of the current game state.
   *
   * @return the string representation of the game state.
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();

    builder.append("Player: ").append(model.getCurrentPlayer().getPlayerType()).append("\n");

    for (int row = 0; row < model.getRows(); row++) {
      for (int col = 0; col < model.getCols(); col++) {
        Posn pos = new Posn(col, row);
        ICell cell = model.getCell(pos);

        if (cell.isPlayable()) {
          ICard card = cell.getCard();
          if (card != null) {
            builder.append(card.getOwner() == PlayerType.RED ? "R" : "B");
          } else {
            builder.append("_");
          }
        } else {
          builder.append(" ");
        }
        builder.append(" ");
      }
      builder.append("\n");
    }

    // Render the player's hand
    builder.append("Hand:\n");
    List<ICard> hand = model.getCurrentPlayer().getHand();
    for (ICard card : hand) {
      builder.append(card.getName()).append(" ");
      builder.append(attackValueToString(card.getAttackValue(Direction.North))).append(" ");
      builder.append(attackValueToString(card.getAttackValue(Direction.South))).append(" ");
      builder.append(attackValueToString(card.getAttackValue(Direction.East))).append(" ");
      builder.append(attackValueToString(card.getAttackValue(Direction.West))).append("\n");
    }

    return builder.toString();
  }

  /**
   * Converts an attack value integer to its string representation, handling special cases.
   *
   * @param value the attack value.
   * @return the string representation of the attack value.
   */
  private String attackValueToString(int value) {
    return value == 10 ? "A" : Integer.toString(value);
  }
}



