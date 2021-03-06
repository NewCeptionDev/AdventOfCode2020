package me.newceptiondev.day4;

import me.newceptiondev.util.Tuple;

import static me.newceptiondev.day4.PassportRequirements.*;

public class Passport {

  private String birthYear;
  private String issueYear;
  private String expirationYear;
  private String height;
  private String hairColor;
  private String eyeColor;
  private String passportId;

  /**
   * Create a Passport from a String
   *
   * @param passportInput String containing Passport Attributes
   */
  public Passport(final String passportInput) {
    parseAttributes(passportInput);
  }

  /**
   * Checks if the Passport is valid regarding the Requirements of Task 1
   *
   * @return True if every Attribute has a Value
   */
  public boolean isValidForTask1() {
    return birthYear != null && issueYear != null && expirationYear != null && height != null && hairColor != null &&
           eyeColor != null && passportId != null;
  }

  /**
   * Checks if the Passport is valid regarding the Requirements of Task 2
   *
   * @return True if every Attribute has a Value, and the Value is within the Requirements
   */
  public boolean isValidForTask2() {
    return isBirthYearValid() && isIssueYearValid() && isExpirationYearValid() && isHeightValid() &&
           isHairColorValid() && isEyeColorValid() && isPassportIdValid();
  }

  /**
   * Parses the Input to Attributes
   *
   * @param input String
   */
  private void parseAttributes(final String input) {
    String[] attributes = input.split(" ");

    for(String attribute : attributes) {
      String[] keyAndValue = attribute.split(":");

      mapInputToCorrectAttribute(keyAndValue[0], keyAndValue[1]);
    }
  }

  /**
   * Sets the Value of the Attribute corresponding to the Key
   *
   * @param key   Attribute
   * @param value Value
   */
  private void mapInputToCorrectAttribute(final String key, final String value) {
    switch(key) {
      case "byr":
        birthYear = value;
        break;
      case "iyr":
        issueYear = value;
        break;
      case "eyr":
        expirationYear = value;
        break;
      case "hgt":
        height = value;
        break;
      case "hcl":
        hairColor = value;
        break;
      case "ecl":
        eyeColor = value;
        break;
      case "pid":
        passportId = value;
        break;
    }
  }

  /**
   * Tries to parse the given String to an Integer
   *
   * @param input Input
   *
   * @return Integer or null
   */
  private Integer parseStringToInteger(final String input) {
    try {
      return Integer.parseInt(input);
    } catch(Exception ignored) {
      return null;
    }
  }

  /**
   * Checks if the BirthYear is valid regarding the Requirements of Task 2
   *
   * @return True if BirthYear is a number and within the Requirements
   */
  private boolean isBirthYearValid() {
    Integer birthYearAsNumber = parseStringToInteger(birthYear);

    return birthYearAsNumber != null && birthYearAsNumber >= BIRTH_YEAR_LOWER && birthYearAsNumber <= BIRTH_YEAR_UPPER;
  }

  /**
   * Checks if the IssueYear is valid regarding the Requirements of Task 2
   *
   * @return True if IssueYear is a number and within the Requirements
   */
  private boolean isIssueYearValid() {
    Integer issueYearAsNumber = parseStringToInteger(issueYear);

    return issueYearAsNumber != null && issueYearAsNumber >= ISSUE_YEAR_LOWER && issueYearAsNumber <= ISSUE_YEAR_UPPER;
  }

  /**
   * Checks if the ExpirationYear is valid regarding the Requirements of Task 2
   *
   * @return True if ExpirationYear is a Number and within the Requirements
   */
  private boolean isExpirationYearValid() {
    Integer expirationYearAsNumber = parseStringToInteger(expirationYear);

    return expirationYearAsNumber != null && expirationYearAsNumber >= EXPIRATION_DATE_LOWER &&
           expirationYearAsNumber <= EXPIRATION_DATE_UPPER;
  }

  /**
   * Checks if the Height is valid regarding the Requirements of Task 2
   *
   * @return True if Height has a correct Unit and is within the Requirements
   */
  private boolean isHeightValid() {
    Tuple<String, String> parsedHeight = parseHeightIfPossible(height);

    if(parsedHeight == null) {
      return false;
    }

    Integer heightAsNumber = parseStringToInteger(parsedHeight.getX());

    return heightAsNumber != null && heightWithinBordersOfUnit(heightAsNumber, parsedHeight.getY());
  }

  /**
   * Checks if the HairColor is valid regarding the Requirements of Task 2
   *
   * @return True if HairColor starts with # and has a ColorCode with a Length of six that only contains 0-9 and a-f
   */
  private boolean isHairColorValid() {
    if(hairColor == null) {
      return false;
    }

    String colorCode = hairColor.substring(1);

    return hairColor.charAt(0) == '#' && colorCode.length() == HAIR_COLOR_CODE_LENGTH && colorCode.matches("[0-9a-f]*");
  }

  /**
   * Checks if the EyeColor is valid regarding the Requirements of Task 2
   *
   * @return True if EyeColor is within the List of valid EyeColors
   */
  private boolean isEyeColorValid() {
    return eyeColor != null && EYE_COLORS.contains(eyeColor);
  }

  /**
   * Checks if the PassportId is valid regarding the Requirements of Task 2
   *
   * @return True if PassportId is a number and has a length of nine
   */
  private boolean isPassportIdValid() {
    Integer passportIdAsNumber = parseStringToInteger(passportId);

    return passportIdAsNumber != null && passportId.length() == PASSPORT_ID_LENGTH;
  }

  /**
   * Parses the Height to a Tuple with the current Height and the Unit
   *
   * @param height Input Height
   *
   * @return Tuple with Height and Unit or null if there is no correct Unit
   */
  private Tuple<String, String> parseHeightIfPossible(final String height) {
    if(height != null && height.length() >= 4) {
      String possibleUnit = height.substring(height.length() - 2);

      if(possibleUnit.equals("cm") || possibleUnit.equals("in")) {
        return new Tuple<>(height.substring(0, height.length() - 2), possibleUnit);
      }
    }

    return null;
  }

  /**
   * Checks if the Height is within the Requirements for the given Unit
   *
   * @param height Height that is checked
   * @param unit   Unit of the Height
   *
   * @return True if Height is within the Requirements for the given Unit
   */
  private boolean heightWithinBordersOfUnit(final Integer height, final String unit) {
    switch(unit) {
      case "cm":
        return height >= HEIGHT_METRIC_LOWER && height <= HEIGHT_METRIC_UPPER;
      case "in":
        return height >= HEIGHT_IMPERIAL_LOWER && height <= HEIGHT_IMPERIAL_UPPER;
    }

    return false;
  }
}
