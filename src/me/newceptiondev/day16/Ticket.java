package me.newceptiondev.day16;

import java.util.ArrayList;
import java.util.List;

public class Ticket {

  private final List<Integer> values = new ArrayList<>();

  /**
   * Creates a new Ticket from a description
   *
   * @param description Description
   */
  public Ticket(String description) {
    String[] parts = description.split(",");

    for(String part : parts) {
      values.add(Integer.parseInt(part));
    }
  }

  /**
   * Returns a List containing all invalid Values
   *
   * @param fields List of TicketFields
   *
   * @return List of Integer containing all Values that do not match any Field
   */
  public List<Integer> getInvalidValues(List<TicketField> fields) {
    List<Integer> invalidValues = new ArrayList<>();

    for(Integer value : values) {
      boolean isValid = false;

      for(int i = 0; i < fields.size() && !isValid; i++) {
        isValid = fields.get(i).isValid(value);
      }

      if(!isValid) {
        invalidValues.add(value);
      }
    }

    return invalidValues;
  }
}
