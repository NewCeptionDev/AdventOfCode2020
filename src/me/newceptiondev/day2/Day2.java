package me.newceptiondev.day2;

import me.newceptiondev.util.FileUtil;

import java.util.List;

public final class Day2 {

  private static final String FILE_NAME = "day2Task1Input";

  public static void main(String[] args) {
    List<String> inputs = FileUtil.readFileAsListOfLines(FILE_NAME);

    new Day2(inputs);
  }

  private Day2(List<String> inputs) {
    System.out.println("Task 1: " + task1(inputs));
    System.out.println("Task 2: " + task2(inputs));
  }

  /**
   * Task 1
   *
   * @param inputs List of String
   *
   * @return Count of valid Passwords regarding the Requirements of Task 1
   */
  public int task1(final List<String> inputs) {
    int validPasswords = 0;

    for(String input : inputs) {
      ParsedInput parsedInput = parseInput(input);

      if(parsedInput.isValidForTask1()) {
        validPasswords++;
      }
    }

    return validPasswords;
  }

  /**
   * Task 2
   *
   * @param inputs List of String
   *
   * @return Count of valid Passwords regarding the Requirements of Task 2
   */
  public int task2(final List<String> inputs) {
    int validPasswords = 0;

    for(String input : inputs) {
      ParsedInput parsedInput = parseInput(input);

      if(parsedInput.isValidForTask2()) {
        validPasswords++;
      }
    }

    return validPasswords;
  }

  /**
   * Parses the Input to a ParsedInput Object
   *
   * @param input String that should be parsed
   *
   * @return ParsedInput
   */
  private ParsedInput parseInput(final String input) {
    //Using Split as I do not need to worry about the Letter Counts with this
    String[] informationPasswordSplit = input.split(":");
    String[] rangeLetterSplit = informationPasswordSplit[0].split(" ");
    String[] rangeSplit = rangeLetterSplit[0].split("-");

    String password = informationPasswordSplit[1].trim();
    char requiredCharacter = rangeLetterSplit[1].charAt(0);
    int lowerRangeEnd = Integer.parseInt(rangeSplit[0]);
    int upperRangeEnd = Integer.parseInt(rangeSplit[1]);

    return new ParsedInput(password, requiredCharacter, lowerRangeEnd, upperRangeEnd);
  }

}
