package day1;

import util.FileUtil;
import util.Tuple;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day1 {

    private static final String fileName = "day1Task1Input";
    private static final Integer searchedNumber = 2020;

    public static void main(String[] args) {
        new Day1();
    }

    public Day1() {
        List<String> inputs = FileUtil.readFileAsListOfLines(fileName);

        System.out.println("Task 1: " + task1(inputs));
        System.out.println("Task 2: " + task2(inputs));
    }

    /**
     * Task 1
     *
     * @param inputs List of String
     * @return Product of two Numbers which Sum is equal to searchedNumber
     *         -1 if no Pair of Numbers is found
     */
    public Integer task1(List<String> inputs) {
        HashSet<Integer> lowerThanOrEqualHalf = new HashSet<>();
        HashSet<Integer> higherThanHalf = new HashSet<>();

        //Parses the Inputs to Integer and sorts them
        this.sortInputs(inputs, lowerThanOrEqualHalf, higherThanHalf);

        Tuple<Integer, Integer> result;

        if (lowerThanOrEqualHalf.size() < higherThanHalf.size()) {
            result = this.searchForSecondElement(lowerThanOrEqualHalf, higherThanHalf,
                    searchedNumber);
        } else {
            result = this.searchForSecondElement(higherThanHalf, lowerThanOrEqualHalf,
                    searchedNumber);
        }

        if (result == null) {
            System.err.println("There was no Element matching the Criteria");
            return -1;
        }

        return result.getX() * result.getY();
    }

    /**
     * Task 2
     *
     * @param inputs List of String
     * @return Product of three Numbers which Sum is equal to searchedNumber
     *         -1 if there is no Combination of numbers
     */
    public Integer task2(List<String> inputs) {
        HashSet<Integer> lowerThanOrEqualHalf = new HashSet<>();
        HashSet<Integer> higherThanHalf = new HashSet<>();

        //Parses the Inputs to Integer and sorts them
        this.sortInputs(inputs, lowerThanOrEqualHalf, higherThanHalf);

        Tuple<Integer, Tuple<Integer, Integer>> result;

        if (lowerThanOrEqualHalf.size() < higherThanHalf.size()) {
            result = this.searchForSecondAndThirdElement(lowerThanOrEqualHalf, higherThanHalf);
        } else {
            result = this.searchForSecondAndThirdElement(higherThanHalf, lowerThanOrEqualHalf);
        }

        if (result == null) {
            System.err.println("There was no Element matching the Criteria");
            return -1;
        }

        return result.getX() * result.getY().getX() * result.getY().getY();
    }

    /**
     * Runs over the smaller Set and searches for the corresponding Number in the second Set
     *
     * @param smallerSet   The Set with less Numbers in it
     * @param largerSet    The Set with more Numbers in it
     * @param sumOfNumbers Should be the Result of the Addition of both Numbers
     * @return Tuple with two Integers which Sum is equal to sumOfNumbers
     *          Null if there is no Combination
     */
    private Tuple<Integer, Integer> searchForSecondElement(Set<Integer> smallerSet, Set<Integer> largerSet, Integer sumOfNumbers) {
        for (int currentElement : smallerSet) {
            int missingPiece = sumOfNumbers - currentElement;

            if (largerSet.contains(missingPiece)) {
                return new Tuple<>(currentElement, missingPiece);
            }
        }

        return null;
    }

    /**
     * Runs over the smaller Set and searches for a corresponding Tuple of Integer
     *
     * @param smallerSet The Set with less Numbers in it
     * @param largerSet  The Set with more Numbers in it
     * @return Tuple with an Integer and a Tuple of two Integers which Sum is equal to the searchesNumber
     *         Null if there is no Combination
     */
    private Tuple<Integer, Tuple<Integer, Integer>> searchForSecondAndThirdElement(Set<Integer> smallerSet, Set<Integer> largerSet) {
        for (int currentElement : smallerSet) {
            int searchedSumOfRemainingNumbers = searchedNumber - currentElement;

            Tuple<Integer, Integer> result = this.searchForSecondElement(
                    createModifiedSet(smallerSet, currentElement), largerSet,
                    searchedSumOfRemainingNumbers);

            if (result != null) {
                return new Tuple<>(currentElement, result);
            }
        }

        return null;
    }

    /**
     * Clones the given Set and removes the given Element from it
     *
     * @param input    Set to be clones
     * @param toRemove Element to be removed
     * @return Modified Version of input
     */
    private HashSet<Integer> createModifiedSet(Set<Integer> input, Integer toRemove) {
        HashSet<Integer> modifiedSet = new HashSet<>(input);
        modifiedSet.remove(toRemove);

        return modifiedSet;
    }

    /**
     * Sorts the Inputs
     *
     * @param inputs         List of Inputs
     * @param lowerThanEqual Set of all Integer in the List that are less or equal to the Half of the searchedNumber
     * @param higher         Set of all Integer in the List that are higher than Half of the searchedNumber
     */
    private void sortInputs(List<String> inputs, Set<Integer> lowerThanEqual, Set<Integer> higher) {
        for (String input : inputs) {
            int parsedInput = Integer.parseInt(input);

            if (parsedInput <= (searchedNumber / 2)) {
                lowerThanEqual.add(parsedInput);
            } else {
                higher.add(parsedInput);
            }
        }
    }

}
