package me.newceptiondev.day16;

import me.newceptiondev.util.FileUtil;
import me.newceptiondev.util.Tuple;

import java.util.*;

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

    public Day16() {
    }

    /**
     * Task 1
     *
     * @param inputs List of String
     *
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
     *
     * @return Ticket My Ticket
     */
    private Ticket parseInput(List<String> inputs, List<TicketField> fields, List<Ticket> nearbyTickets) {
        Ticket myTicket = null;
        int emptyRows = 0;

        for(String input : inputs) {
            if(input.equals("")) {
                emptyRows++;
            } else if(emptyRows == 0) {
                fields.add(new TicketField(input));
            } else if(emptyRows == 1) {
                if(!input.equals("your ticket:")) {
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
    public long task2(List<String> inputs) {
        List<TicketField> fields = new ArrayList<>();
        List<Ticket> nearbyTickets = new ArrayList<>();

        Ticket ownTicket = parseInput(inputs, fields, nearbyTickets);

        List<Ticket> validTickets =
            nearbyTickets.stream().filter(ticket -> ticket.getInvalidValues(fields).isEmpty()).toList();

        Queue<Map<Integer, TicketField>> states = new LinkedList<>();
        states.add(new HashMap<>());

        while(!states.isEmpty()) {
            Map<Integer, TicketField> correctPosition = states.poll();
            Map<Integer, List<TicketField>> possible = new HashMap<>();

            if(correctPosition.values().size() == ownTicket.getValueCount()) {
                long result = 1;
                for(int key : correctPosition.keySet()) {
                    if(correctPosition.get(key).getName().startsWith("departure")) {
                        result *= ownTicket.getValueAtPosition(key);
                    }
                }

                return result;
            }

            for(int pos = 0; pos < ownTicket.getValueCount(); pos++) {
                if(!correctPosition.containsKey(pos)) {
                    possible.put(pos, new ArrayList<>());

                    for(TicketField field : fields.stream().filter(possibleField -> !correctPosition.containsValue(possibleField)).toList()) {
                        boolean isPossible = true;

                        for(int i = 0; i < validTickets.size() && isPossible; i++) {
                            isPossible = field.isValid(validTickets.get(i).getValueAtPosition(pos));
                        }

                        if(isPossible) {
                            possible.get(pos).add(field);
                        }
                    }

                }
            }

            boolean addedAtLeastOne = false;
            for(int position : possible.keySet()) {
                if(possible.get(position).size() == 1) {
                    correctPosition.put(position, possible.get(position).get(0));
                    addedAtLeastOne = true;
                }
            }

            if(addedAtLeastOne) {
                states.add(correctPosition);
            } else {
                for(int position : possible.keySet()) {
                    if(possible.get(position).size() == 2) {
                        for(int i = 0; i < 2; i++) {
                            Map<Integer, TicketField> newCorrect = new HashMap<>(correctPosition);
                            newCorrect.put(position, possible.get(position).get(i));

                            states.add(newCorrect);
                        }
                    }
                }
            }
        }

        return 0;
    }

}
