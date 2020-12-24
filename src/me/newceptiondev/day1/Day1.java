package me.newceptiondev.day1;

import me.newceptiondev.util.FileUtil;
import me.newceptiondev.util.Tuple;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class Day1 {

  private static final String FILE_NAME = "day1Task1Input";
  private static final Integer SEARCHED_NUMBER = 2020;

  public static void main(String[] args) {
    List<String> inputs = FileUtil.readFileAsListOfLines(FILE_NAME);

    new Day1(inputs);
  }

  private Day1(List<String> inputs) {
    System.out.println("Task 1: " + task1(inputs));
    System.out.println("Task 2: " + task2(inputs));
  }

  /**
   * Task 1
   *
   * @param inputs List of String
   *
   * @return Product of two Numbers, which Sum is equal to searchedNumber -1 if no Pair of Numbers is found
   */
  public Integer task1(final List<String> inputs) {
    Set<Integer> lowerThanOrEqualHalf = new HashSet<>();
    Set<Integer> higherThanHalf = new HashSet<>();

    //Parses the Inputs to Integer and sorts them
    sortInputs(inputs, lowerThanOrEqualHalf, higherThanHalf);

    Tuple<Integer, Integer> result;

    if(lowerThanOrEqualHalf.size() < higherThanHalf.size()) {
      result = searchForSecondElement(lowerThanOrEqualHalf, higherThanHalf, SEARCHED_NUMBER);
    } else {
      result = searchForSecondElement(higherThanHalf, lowerThanOrEqualHalf, SEARCHED_NUMBER);
    }

    if(result == null) {
      System.err.println("There was no Element matching the Criteria");
      return -1;
    }

    return result.getX() * result.getY();
  }

  /**
   * Task 2
   *
   * @param inputs List of String
   *
   * @return Product of three Numbers, which Sum is equal to searchedNumber -1 if there is no Combination of numbers
   */
  public Integer task2(final List<String> inputs) {
    Set<Integer> lowerThanOrEqualHalf = new HashSet<>();
    Set<Integer> higherThanHalf = new HashSet<>();

    //Parses the Inputs to Integer and sorts them
    sortInputs(inputs, lowerThanOrEqualHalf, higherThanHalf);

    Tuple<Integer, Tuple<Integer, Integer>> result;

    if(lowerThanOrEqualHalf.size() < higherThanHalf.size()) {
      result = searchForSecondAndThirdElement(lowerThanOrEqualHalf, higherThanHalf);
    } else {
      result = searchForSecondAndThirdElement(higherThanHalf, lowerThanOrEqualHalf);
    }

    if(result == null) {
      System.err.println("There was no Element matching the Criteria");
      return -1;
    }

    return result.getX() * result.getY().getX() * result.getY().getY();
  }

  /**
   * Sorts the Inputs
   *
   * @param inputs         List of Inputs
   * @param lowerThanEqual Set of all Integer in the List that are less or equal to the Half of the searchedNumber
   * @param higher         Set of all Integer in the List that are higher than Half of the searchedNumber
   */
  private void sortInputs(final List<String> inputs, Set<Integer> lowerThanEqual, Set<Integer> higher) {
    for(String input : inputs) {
      int parsedInput = Integer.parseInt(input);

      if(parsedInput <= (SEARCHED_NUMBER / 2)) {
        lowerThanEqual.add(parsedInput);
      } else {
        higher.add(parsedInput);
      }
    }
  }

  /**
   * Runs over the smaller Set and searches for the corresponding Number in the second Set
   *
   * @param smallerSet   The Set with fewer Numbers in it
   * @param largerSet    The Set with more Numbers in it
   * @param sumOfNumbers Should be the Result of the Addition of both Numbers
   *
   * @return Tuple with two Integers, which Sum is equal to sumOfNumbers Null if there is no Combination
   */
  private Tuple<Integer, Integer> searchForSecondElement(final Set<Integer> smallerSet, final Set<Integer> largerSet,
                                                         final Integer sumOfNumbers) {
    for(int currentElement : smallerSet) {
      int missingPiece = sumOfNumbers - currentElement;

      if(largerSet.contains(missingPiece)) {
        return new Tuple<>(currentElement, missingPiece);
      }
    }

    return null;
  }

  /**
   * Runs over the smaller Set and searches for a corresponding Tuple of Integer
   *
   * @param smallerSet The Set with fewer Numbers in it
   * @param largerSet  The Set with more Numbers in it
   *
   * @return Tuple with an Integer, and a Tuple of two Integers, which Sum is equal to the searchesNumber Null if there
   * is no Combination
   */
  private Tuple<Integer, Tuple<Integer, Integer>> searchForSecondAndThirdElement(final Set<Integer> smallerSet,
                                                                                 final Set<Integer> largerSet) {
    for(int currentElement : smallerSet) {
      int searchedSumOfRemainingNumbers = SEARCHED_NUMBER - currentElement;

      Tuple<Integer, Integer> result = searchForSecondElement(createModifiedSet(smallerSet, currentElement), largerSet,
                                                              searchedSumOfRemainingNumbers);

      if(result != null) {
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
   *
   * @return Modified Version of input
   */
  private HashSet<Integer> createModifiedSet(final Set<Integer> input, final Integer toRemove) {
    HashSet<Integer> modifiedSet = new HashSet<>(input);
    modifiedSet.remove(toRemove);

    return modifiedSet;
  }

}
