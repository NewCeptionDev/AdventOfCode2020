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
    public int task1(final List<String> inputs) {
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
    public long task2(final List<String> inputs) {
        List<Integer> jolts = inputs.stream().map(Integer::parseInt).collect(Collectors.toList());
        List<Integer> joltsSorted = jolts.stream().sorted().collect(Collectors.toList());
        joltsSorted.add(0, 0);
        joltsSorted.add(joltsSorted.size(), joltsSorted.get(joltsSorted.size() - 1) + 3);

        List<List<Integer>> splitByThreeJoltJumps = splitByThreeJoltJumps(joltsSorted);

        return splitByThreeJoltJumps.stream().map(this::returnDistinctArrangements).filter(integer -> integer != 0).map(integer -> (long) integer).reduce(1L, (integer, integer2) -> integer * integer2);
    }

    /**
     * Splits the List of Integer into multiple Lists
     * Integer are split when the Difference between two Integer is 3
     *
     * @param joltsSorted List of Integer
     * @return List of List of Integer
     */
    private List<List<Integer>> splitByThreeJoltJumps(final List<Integer> joltsSorted) {
        List<List<Integer>> splitList = new ArrayList<>();

        List<Integer> currentList = new ArrayList<>();

        for (int i = 0; i < joltsSorted.size() - 1; i++) {
            int distance = joltsSorted.get(i + 1) - joltsSorted.get(i);

            currentList.add(joltsSorted.get(i));

            if (distance == 3) {
                splitList.add(currentList);
                currentList = new ArrayList<>();
            }
        }

        currentList.add(joltsSorted.get(joltsSorted.size() - 1));
        splitList.add(currentList);

        return splitList;
    }

    /**
     * Calculates and counts all distinct Ways to traverse the List
     *
     * @param jolts List of Integer
     * @return Count of distinct Ways
     */
    private int returnDistinctArrangements(final List<Integer> jolts) {
        int possibilities = 0;

        if(jolts.size() == 1){
            return 1;
        }

        if(jolts.size() > 1){
            possibilities += returnDistinctArrangements(jolts.subList(1, jolts.size()));
        }

        if(jolts.size() > 2 && jolts.get(2) - jolts.get(0) <= 3){
            possibilities += returnDistinctArrangements(jolts.subList(2, jolts.size()));
        }

        if(jolts.size() > 3 && jolts.get(3) - jolts.get(0) <= 3) {
            possibilities += returnDistinctArrangements(jolts.subList(3, jolts.size()));
        }

        return possibilities;
    }
}
