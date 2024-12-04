package cs3500.hw05.model.configs;

import cs3500.hw05.card.Card;
import cs3500.hw05.card.Card.CardNumber;
import cs3500.hw05.card.Direction;

import cs3500.hw05.card.ICard;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Scanner;

/**
 * A parser class for reading card databases from configuration files.
 * Supports parsing from both file paths and InputStreams.
 */
public class CardDatabaseParser {

  /**
   * Parses a card database file and returns a list of Card objects.
   *
   * @param filePath the path to the card database file.
   * @return a list of Card objects.
   * @throws FileNotFoundException if the file is not found.
   * @throws IllegalArgumentException if the file format is invalid.
   */
  public static List<ICard> parseCardDatabase(String filePath)
      throws FileNotFoundException {
    InputStream inputStream = new FileInputStream(filePath);
    return parseCardDatabase(inputStream);
  }

  /**
   * Parses a card database from an InputStream and returns a list of Card objects.
   *
   * @param inputStream the InputStream to read the card database from.
   * @return a list of Card objects.
   * @throws IllegalArgumentException if the input format is invalid.
   */
  public static List<ICard> parseCardDatabase(InputStream inputStream) {
    Scanner scanner = new Scanner(inputStream);
    List<ICard> cards = parseCards(scanner);
    scanner.close();
    return cards;
  }

  /**
   * Parses the cards from a Scanner and returns a list of Card objects.
   *
   * @param scanner the Scanner to read the card data from.
   * @return a list of Card objects.
   * @throws IllegalArgumentException if the input format is invalid.
   */
  private static List<ICard> parseCards(Scanner scanner) {
    List<ICard> cards = new ArrayList<ICard>();

    while (scanner.hasNextLine()) {
      String line = scanner.nextLine().trim();

      // Skip empty lines and comments
      if (line.isEmpty() || line.startsWith("#")) {
        continue;
      }

      String[] tokens = line.split("\\s+");
      if (tokens.length != 5) {
        throw new IllegalArgumentException("Invalid card format: " + line);
      }

      String name = tokens[0];
      EnumMap<Direction, CardNumber> attackValues = new EnumMap<>(Direction.class);

      try {
        attackValues.put(Direction.North, parseCardNumber(tokens[1]));
        attackValues.put(Direction.South, parseCardNumber(tokens[2]));
        attackValues.put(Direction.East,  parseCardNumber(tokens[3]));
        attackValues.put(Direction.West,  parseCardNumber(tokens[4]));
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException("Invalid attack value in line: " + line + ". "
            + e.getMessage());
      }

      cards.add(new Card(name, attackValues));
    }

    return cards;
  }

  /**
   * Parses a string into a CardNumber enum.
   * Supports both integer values and enum names (e.g., "FIVE", "7").
   *
   * @param s the string representation of the card number.
   * @return the corresponding CardNumber enum.
   * @throws IllegalArgumentException if the string cannot be parsed into a CardNumber.
   */
  private static CardNumber parseCardNumber(String s) {
    // Attempt to parse the string as an integer
    try {
      int value = Integer.parseInt(s);
      return CardNumber.getByValue(value);
    } catch (NumberFormatException e) {
      try {
        return CardNumber.valueOf(s.toUpperCase());
      } catch (IllegalArgumentException ex) {
        throw new IllegalArgumentException("Invalid card number: " + s);
      }
    }
  }
}
