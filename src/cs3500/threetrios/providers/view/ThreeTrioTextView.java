package cs3500.threetrios.providers.view;

import java.io.IOException;
import java.util.List;

import cs3500.threetrios.providers.model.Card;
import cs3500.threetrios.providers.model.Cell;
import cs3500.threetrios.providers.model.ReadOnlyModel;

/**
 * Implements the View to render a text-based view of the Model a Three Trio game.
 */
public class ThreeTrioTextView implements View {
  private final ReadOnlyModel model;
  private final Appendable out;

  /**
   * Constructor for a ThreeTrioTextView.
   * @param model the model that represents all behaviors as well as the current state of this
   *              Three Trio game.
   */
  public ThreeTrioTextView(ReadOnlyModel model) {
    this.model = model;
    out = new StringBuilder();
  }


  /**
   * Returns a text-based representation of this view, which involves converting all parts of this
   * ThreeTrioTextView's model to a String.
   *
   * @return a String representing this ThreeTrioTextView's model.
   */
  public String toString() {

    return "Player: "
            + model.getCurPlayer().getColor().toString() + "\n"
            + gridToString()
            + "Hand:\n"
            + handToString();
  }

  private String gridToString() {
    StringBuilder outStringBuilder = new StringBuilder();
    Cell[][] cellGrid = model.getGridArray();
    for (Cell[] cells : cellGrid) {
      StringBuilder row = new StringBuilder();
      for (Cell cell : cells) {
        row.append(cell.toString());
      }
      outStringBuilder.append(row.append("\n"));
    }
    return outStringBuilder.toString();
  }

  private String handToString() {
    StringBuilder outStringBuilder = new StringBuilder();
    List<Card> hand = model.getHand(model.getCurPlayer());
    for (Card card : hand) {
      outStringBuilder.append(card.toString()).append("\n");
    }
    return outStringBuilder.toString().stripTrailing();
  }

  @Override
  public void render() throws IOException {
    out.append(toString());
  }
}
