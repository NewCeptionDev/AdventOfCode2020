package me.newceptiondev.day5;

import java.util.List;
import java.util.stream.Collectors;

public class Seat {

  private static final int COLUMN_LOWER_END = 0;
  private static final int COLUMN_UPPER_END = 7;
  private static final char COLUMN_LOWER_INDICATOR = 'L';
  private static final int ROW_LOWER_END = 0;
  private static final int ROW_UPPER_END = 127;
  private static final char ROW_LOWER_INDICATOR = 'F';
  private int row;
  private int column;

  public Seat(final String code) {
    createSeatFromCode(code);
  }

  public Seat(final int row, final int column) {
    this.row = row;
    this.column = column;
  }

  /**
   * Calculates the Seat ID
   *
   * @return SeatId
   */
  public int calculateSeatId() {
    return (row * 8) + column;
  }

  /**
   * Parses the Row and Column from a given Code
   *
   * @param code The Code that contains Row and Column
   */
  private void createSeatFromCode(final String code) {
    String rowCode = code.substring(0, 7);
    String columnCode = code.substring(7);

    row = parseRow(rowCode);
    column = parseColumn(columnCode);
  }

  /**
   * Parses the Column
   *
   * @param columnCode Part of Code that describes the Column
   *
   * @return Column
   */
  private int parseColumn(final String columnCode) {
    List<Character> columnCodeAsCharList = parseStringToCharList(columnCode);

    return findCorrectPartition(columnCodeAsCharList, COLUMN_LOWER_END, COLUMN_UPPER_END, COLUMN_LOWER_INDICATOR);
  }

  /**
   * Parses the Row
   *
   * @param rowCode Part of Code that describes the Row
   *
   * @return Row
   */
  private int parseRow(final String rowCode) {
    List<Character> rowCodeAsCharacter = parseStringToCharList(rowCode);

    return findCorrectPartition(rowCodeAsCharacter, ROW_LOWER_END, ROW_UPPER_END, ROW_LOWER_INDICATOR);
  }

  /**
   * Find the relevant Partition by the given Indicator Gets called recursively
   *
   * @param indicators     List of Indicators
   * @param lowerEnd       Lower End of Partition
   * @param upperEnd       Upper End of Partition
   * @param lowerIndicator Indicator to take the lower Half of the Partition
   *
   * @return Partition of Size 1 that is described by the Indicators
   */
  private int findCorrectPartition(final List<Character> indicators, final int lowerEnd, final int upperEnd,
                                   final Character lowerIndicator) {

    //Exit Condition
    if(indicators.size() == 1) {
      if(indicators.get(0) == lowerIndicator) {
        return lowerEnd;
      }
      return upperEnd;
    }

    int shiftCount = getShiftCount(lowerEnd, upperEnd);
    List<Character> newIndicators = indicators.subList(1, indicators.size());

    if(indicators.get(0) == lowerIndicator) {
      return findCorrectPartition(newIndicators, lowerEnd, upperEnd - shiftCount, lowerIndicator);
    }

    return findCorrectPartition(newIndicators, lowerEnd + shiftCount, upperEnd, lowerIndicator);
  }

  /**
   * Calculates by how much the Partition shrinks
   *
   * @param lowerEnd Lower End of Partition
   * @param upperEnd Upper End of Partition
   *
   * @return Shrink Count of Partition
   */
  private int getShiftCount(final int lowerEnd, final int upperEnd) {
    return (upperEnd - lowerEnd + 1) / 2;
  }

  /**
   * Parses a String to a List of Character
   *
   * @param input String
   *
   * @return List of Character
   */
  private List<Character> parseStringToCharList(final String input) {
    return input.chars().mapToObj(character -> (char) character).collect(Collectors.toList());
  }

  /**
   * Getter for Row
   *
   * @return Row
   */
  public int getRow() {
    return row;
  }

  /**
   * Getter for Column
   *
   * @return Column
   */
  public int getColumn() {
    return column;
  }
}
