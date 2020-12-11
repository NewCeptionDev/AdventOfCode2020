package me.newceptiondev.day10;

import me.newceptiondev.util.FileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day10 {

    public static final String fileName = "day10Task1Input";

    public static void main(String[] args) {
        List<String> inputs = FileUtil.readFileAsListOfLines(fileName);

        new Day10(inputs);
    }

    public Day10() {
    }

    public Day10(List<String> inputs) {
        System.out.println("Task 1: " + task1(inputs));
        System.out.println("Task 2: " + task2(inputs));
    }

    /**
     * Task 1
     *
     * @param inputs List of String
     * @return Product of 1-Difference and 3-Difference
     */
    public int task1(List<String> inputs) {
        List<Integer> jolts = inputs.stream().map(Integer::parseInt).collect(Collectors.toList());
        List<Integer> joltsSorted = jolts.stream().sorted().collect(Collectors.toList());
        joltsSorted.add(0, 0);
        joltsSorted.add(joltsSorted.size(), joltsSorted.get(joltsSorted.size() - 1) + 3);

        int differentByOne = 0;
        int differentByThree = 0;

        for (int i = 1; i < joltsSorted.size(); i++) {
            int difference = joltsSorted.get(i) - joltsSorted.get(i - 1);

            if (difference == 1) {
                differentByOne++;
            } else if (difference == 3) {
                differentByThree++;
            }
        }

        return differentByOne * differentByThree;
    }

    /**
     * Task 2
     *
     * @param inputs List of String
     * @return Number of distinct Arrangements that connect the Device to the Charging Outlet
     */
    public int task2(List<String> inputs) {
        List<Integer> jolts = inputs.stream().map(Integer::parseInt).collect(Collectors.toList());
        List<Integer> joltsSorted = jolts.stream().sorted().collect(Collectors.toList());
        joltsSorted.add(0, 0);
        joltsSorted.add(joltsSorted.size(), joltsSorted.get(joltsSorted.size() - 1) + 3);

        //TODO Count all Possibilites between two 3 Jumps and Multiply them

        return returnDistinctArrangements(joltsSorted);
    }

    private int returnDistinctArrangements(List<Integer> jolts) {
        int possibleSolutions = 0;

        if (jolts.contains(jolts.get(0) + 1)) {
            List<Integer> modifiedList = new ArrayList<>(jolts.subList(jolts.indexOf(jolts.get(0) + 1), jolts.size()));

            possibleSolutions += returnDistinctArrangements(modifiedList);
        }

        if (jolts.contains(jolts.get(0) + 2)) {
            List<Integer> modifiedList = new ArrayList<>(jolts.subList(jolts.indexOf(jolts.get(0) + 2), jolts.size()));

            possibleSolutions += returnDistinctArrangements(modifiedList);
        }

        if (jolts.contains(jolts.get(0) + 3)) {
            List<Integer> modifiedList = new ArrayList<>(jolts.subList(jolts.indexOf(jolts.get(0) + 3), jolts.size()));

            possibleSolutions += returnDistinctArrangements(modifiedList);
        }

        return possibleSolutions;
    }

}
