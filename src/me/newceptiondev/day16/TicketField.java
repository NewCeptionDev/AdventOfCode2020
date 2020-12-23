package me.newceptiondev.day16;

import java.util.ArrayList;
import java.util.List;

public class TicketField {

  private final String name;
  private final List<ValidationRange> validationRanges = new ArrayList<>();

  /**
   * Creates a TicketField from a description
   *
   * @param description Description
   */
  public TicketField(String description) {
    String[] nameAndValueSplit = description.split(":");
    name = nameAndValueSplit[0];

    String[] values = nameAndValueSplit[1].split("or");

    for(String value : values) {
      validationRanges.add(new ValidationRange(value.trim()));
    }
  }

  /**
   * Checks whether the given number is valid or not
   *
   * @param number Number to be tested
   *
   * @return True if number is within any of the Ranges
   */
  public boolean isValid(int number) {
    boolean isValid = false;

    for(int i = 0; i < validationRanges.size() && !isValid; i++) {
      isValid = validationRanges.get(i).isValid(number);
    }

    return isValid;
  }
}
