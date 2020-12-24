package me.newceptiondev.day16;

import me.newceptiondev.util.FileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class Day16 {

    private static final String FILE_NAME = "day16Task1Input";

    public static void main(String[] args) {
        List<String> inputs = FileUtil.readFileAsListOfLines(FILE_NAME);

        new Day16(inputs);
    }

    private Day16(List<String> inputs) {
        System.out.println("Task 1: " + task1(inputs));
        System.out.println("Task 2: " + task2(inputs));
    }

    /**
     * Task 1
     *
     * @param inputs List of String
     * @return Scanning Error Rate of nearby Tickets
     */
    public int task1(List<String> inputs) {

        List<TicketField> fields = new ArrayList<>();
        List<Ticket> nearbyTickets = new ArrayList<>();

        parseInput(inputs, fields, nearbyTickets);

        List<Integer> invalidValues = new ArrayList<>();

        for(Ticket ticket : nearbyTickets) {
            invalidValues.addAll(ticket.getInvalidValues(fields));
        }

        Optional<Integer> scanningErrorRate = invalidValues.stream().reduce(Integer::sum);

        if(scanningErrorRate.isPresent()) {
            return scanningErrorRate.get();
        }

        System.err.println("There was an Error summing up the Invalid Values");
        return 0;
    }

    /**
     * Parses the Input
     *
     * @param inputs        List of String
     * @param fields        List of TicketField Gets filled within this Method
     * @param nearbyTickets List of Ticket Gets filled within this Method
     * @return Ticket My Ticket
     */
    private Ticket parseInput(List<String> inputs, List<TicketField> fields, List<Ticket> nearbyTickets) {
        Ticket myTicket = null;
        int emptyRows = 0;

        for (String input : inputs) {
            if (input.equals("")) {
                emptyRows++;
            } else if (emptyRows == 0) {
                fields.add(new TicketField(input));
            } else if (emptyRows == 1) {
                if (!input.equals("your ticket:")) {
                    myTicket = new Ticket(input);
                }
            } else if(emptyRows == 2) {
              if(!input.equals("nearby tickets:")) {
                nearbyTickets.add(new Ticket(input));
              }
            }
        }

      return myTicket;
    }

  /**
   * Task 2
   *
   * @param inputs List of String
   *
   * @return
   */
  public int task2(List<String> inputs) {
    return 0;
  }

}
