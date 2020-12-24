package me.newceptiondev.day2;

import java.util.HashMap;
import java.util.Map;

public class ParsedInput {

  private final String password;
  private final char requiredCharacter;
  private final int firstPosition;
  private final int secondPosition;

  public ParsedInput(final String password, final char requiredCharacter, final int firstPosition,
                     final int secondPosition) {
    this.password = password;
    this.requiredCharacter = requiredCharacter;
    this.firstPosition = firstPosition;
    this.secondPosition = secondPosition;
  }

  /**
   * Checks if the ParsedInput is a Valid Input regarding the Requirements of Task 1
   *
   * @return True if the Amount of requiredCharacter in the Password is inclusive between firstPosition and
   * secondPosition
   */
  public boolean isValidForTask1() {
    Map<Character, Integer> lettersOfPassword = mapPasswordStringToLetters();

    if(lettersOfPassword.containsKey(requiredCharacter)) {
      int amount = lettersOfPassword.get(requiredCharacter);

      return firstPosition <= amount && amount <= secondPosition;
    }

    return false;
  }

  /**
   * Maps the Password to its Letters, and their Occurrence Count
   *
   * @return Map with all different Letters and their Occurrence Count
   */
  private Map<Character, Integer> mapPasswordStringToLetters() {
    Map<Character, Integer> letters = new HashMap<>();

    for(int i = 0; i < password.length(); i++) {
      char letter = password.charAt(i);

      if(letters.containsKey(letter)) {
        letters.put(letter, letters.get(letter) + 1);
      } else {
        letters.put(letter, 1);
      }
    }

    return letters;
  }

  /**
   * Checks if the ParsedInput is a Valid Input regarding the Requirements of Task 2
   *
   * @return True if the required Character is at exactly one Position of firstPosition or secondPosition
   */
  public boolean isValidForTask2() {
    //Manipulating Position with - 1 as Toboggan Corporate Policies have no concept of "index zero"
    boolean correctCharacterAtFirstPosition =
        firstPosition - 1 >= 0 && password.charAt(firstPosition - 1) == requiredCharacter;
    boolean correctCharacterAtSecondPosition = password.charAt(secondPosition - 1) == requiredCharacter;

    return correctCharacterAtFirstPosition ^ correctCharacterAtSecondPosition;
  }
}
