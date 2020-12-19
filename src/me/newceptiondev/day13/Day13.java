package me.newceptiondev.day13;

import me.newceptiondev.util.FileUtil;
import me.newceptiondev.util.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day13 {

    public static final String fileName = "day13Task1Input";

    public static void main(String[] args) {
        List<String> inputs = FileUtil.readFileAsListOfLines(fileName);

        new Day13(inputs);
    }

    public Day13() {
    }

    public Day13(List<String> inputs) {
        System.out.println("Task 1: " + task1(inputs));
        System.out.println("Task 2: " + task2(inputs));
    }

    /**
     * Task 1
     *
     * @param inputs List of String
     * @return Product of Bus Id and Minutes of Wait
     */
    public int task1(List<String> inputs) {
        int timeToAirport = Integer.parseInt(inputs.get(0));
        List<Integer> busIds = parseBusIds(inputs.get(1));

        Tuple<Integer, Integer> earliestBus = null;

        for (Integer busId : busIds) {
            if (busId != null) {
                int waitingTime = busId - (timeToAirport % busId);

                if (earliestBus == null || earliestBus.getY() > waitingTime) {
                    earliestBus = new Tuple<>(busId, waitingTime);
                }
            }
        }

        if (earliestBus != null) {
            return earliestBus.getX() * earliestBus.getY();
        }

        System.err.println("There was no valid BusId within the Input");
        return -1;
    }

    /**
     * Task 2
     *
     * @param inputs List of String
     * @return Earliest Timestamp that matches Requirements of Task 2
     */
    public Long task2(List<String> inputs) {
        List<Integer> busIds = parseBusIds(inputs.get(1));

        int highestNumber = findHighestInteger(busIds);

        List<Tuple<Integer, Integer>> idsWithIndex = busIds.stream().map(integer -> new Tuple<>(
                integer, busIds.indexOf(integer))).filter(
                integerIntegerTuple -> integerIntegerTuple.getX() != null).collect(
                Collectors.toList());

        Long timestamp = null;
        long currentMultiplier = 100000000000000L / highestNumber;

        while (timestamp == null) {
            long possibleTimestamp = (highestNumber * currentMultiplier) - busIds.indexOf(
                    highestNumber);

            boolean valid = true;

            for (int i = 0; i < idsWithIndex.size() && valid; i++) {
                valid = possibleTimestamp % idsWithIndex.get(i).getX() == (idsWithIndex.get(
                        i).getX() - idsWithIndex.get(i).getY()) % idsWithIndex.get(
                        i).getX();
            }

            if (valid) {
                timestamp = possibleTimestamp;
            }

            currentMultiplier++;
        }

        return timestamp;
    }

    private List<Integer> parseBusIds(String input) {
        List<Integer> parsedBusIds = new ArrayList<>();
        String[] busIds = input.split(",");

        for (String id : busIds) {
            if (!id.equals("x")) {
                parsedBusIds.add(Integer.parseInt(id));
            } else {
                parsedBusIds.add(null);
            }
        }

        return parsedBusIds;
    }

    private Integer findHighestInteger(List<Integer> input) {
        Integer highestInteger = null;

        for (Integer integer : input) {
            if (integer != null) {
                if (highestInteger == null || highestInteger < integer) {
                    highestInteger = integer;
                }
            }
        }

        return highestInteger;
    }
}
