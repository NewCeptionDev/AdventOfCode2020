package me.newceptiondev.day15;

import me.newceptiondev.util.Tuple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day15 {

    public static final List<Integer> input = List.of(16, 11, 15, 0, 1, 7);

    public static final int SEARCHED_INDEX_TASK1 = 2020;
    public static final int SEARCHED_INDEX_TASK2 = 30000000;

    public static void main(String[] args) {
        new Day15(input);
    }

    public Day15() {
    }

    public Day15(List<Integer> inputs) {
        System.out.println("Task 1: " + task1(inputs));
        System.out.println("Task 2: " + task2(inputs));
    }

    /**
     * Task 1
     *
     * @param inputs List of Integer
     * @return Number that is spoken at Position SEARCHED_INDEX_TASK1
     */
    public int task1(List<Integer> inputs) {
        Map<Integer, Tuple<Integer, Integer>> numberAndLastIndices = createMapWithStartingNumbers(inputs);

        int index = inputs.size();
        int currentNumber = 0;

        while (index < SEARCHED_INDEX_TASK1) {
            currentNumber = calculateNextNumber(currentNumber, numberAndLastIndices);
            updateNumberAndLastIndexMap(numberAndLastIndices, index, currentNumber);

            index++;
        }

        return currentNumber;
    }

    /**
     * Task 2
     *
     * @param inputs List of Integer
     * @return Number that is spoken at Position SEARCHED_INDEX_TASK2
     */
    public int task2(List<Integer> inputs) {
        Map<Integer, Tuple<Integer, Integer>> numberAndLastIndices = createMapWithStartingNumbers(inputs);

        int index = inputs.size();
        int currentNumber = 0;

        while (index < SEARCHED_INDEX_TASK2) {
            currentNumber = calculateNextNumber(currentNumber, numberAndLastIndices);
            updateNumberAndLastIndexMap(numberAndLastIndices, index, currentNumber);

            index++;
        }

        return currentNumber;
    }

    /**
     * Creates a Map for the StartingNumbers
     *
     * @param inputs List of Integer
     * @return Map with Number and a Tuple with their Latest Index and the Index before that
     */
    private Map<Integer, Tuple<Integer, Integer>> createMapWithStartingNumbers(List<Integer> inputs) {
        Map<Integer, Tuple<Integer, Integer>> numberAndLastIndices = new HashMap<>();

        for (int i = 0; i < inputs.size(); i++) {
            numberAndLastIndices.put(inputs.get(i), new Tuple<>(i, null));
        }

        return numberAndLastIndices;
    }

    /**
     * Calculates the Next Number based on the Last Number and the Indices of the Numbers
     *
     * @param lastNumber Last spoken Number
     * @param numberAndLastIndex Numbers already spoken with the Indices of the last Time spoken and the Time before that
     * @return Next Number spoken
     */
    private int calculateNextNumber(int lastNumber, Map<Integer, Tuple<Integer, Integer>> numberAndLastIndex) {

        if (numberAndLastIndex.get(lastNumber).getY() == null) {
            return 0;
        } else {
            return numberAndLastIndex.get(lastNumber).getX() - numberAndLastIndex.get(
                    lastNumber).getY();
        }

    }

    /**
     * Updates the Map containing spoken Numbers and their last two Indices
     *
     * @param numberAndLastIndices Map with Numbers and their Indices
     * @param index Current Index
     * @param currentNumber Current Number
     */
    private void updateNumberAndLastIndexMap(Map<Integer, Tuple<Integer, Integer>> numberAndLastIndices, int index, int currentNumber) {
        if (!numberAndLastIndices.containsKey(currentNumber)) {
            numberAndLastIndices.put(currentNumber, new Tuple<>(index, null));
        } else {
            numberAndLastIndices.put(currentNumber,
                    new Tuple<>(index, numberAndLastIndices.get(currentNumber).getX()));
        }
    }

}
