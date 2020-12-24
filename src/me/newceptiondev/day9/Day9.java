package me.newceptiondev.day9;

import me.newceptiondev.util.FileUtil;
import me.newceptiondev.util.Tuple;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class Day9 {

  private static final String FILE_NAME = "day9Task1Input";
  private static final int PREAMBLE_SIZE = 25;

  public static void main(String[] args) {
    List<String> inputs = FileUtil.readFileAsListOfLines(FILE_NAME);

    new Day9(inputs);
  }

  private Day9(final List<String> inputs) {
    Long invalidNumber = task1(inputs);

    System.out.println("Task 1: " + invalidNumber);
    System.out.println("Task 2: " + task2(inputs, invalidNumber));
  }

  /**
   * Task 1
   *
   * @param inputs List of String
   *
   * @return Return first invalid Number
   */
  public long task1(final List<String> inputs) {
    List<Long> allNumbers = inputs.stream().map(Long::parseLong).collect(Collectors.toList());

    Set<Long> preamble = new HashSet<>(allNumbers.subList(0, PREAMBLE_SIZE));

    for(int i = PREAMBLE_SIZE; i < allNumbers.size(); i++) {
      if(findTermsOfSum(allNumbers.get(i), preamble) == null) {
        return allNumbers.get(i);
      } else {
        preamble.remove(allNumbers.get(i - PREAMBLE_SIZE));
        preamble.add(allNumbers.get(i));
      }
    }

    return 0;
  }

  /**
   * Tries to find two Numbers that add up to the given Sum
   *
   * @param sum           Number that should be the Result of an Addition
   * @param possibleTerms Possible Terms of the Sum
   *
   * @return Tuple containing both Terms of the Sum
   */
  private Tuple<Long, Long> findTermsOfSum(final Long sum, final Set<Long> possibleTerms) {

    for(Long summand : possibleTerms) {
      Long neededSecondSummand = sum - summand;

      if(!summand.equals(neededSecondSummand) && possibleTerms.contains(neededSecondSummand)) {
        return new Tuple<>(summand, neededSecondSummand);
      }
    }

    return null;
  }

  /**
   * Task 2
   *
   * @param inputs List of String
   *
   * @return Sum of lowest and highest Number of Set that Sum is the invalid Number
   */
  public long task2(final List<String> inputs, final Long invalidNumber) {
    List<Long> allNumbers = inputs.stream().map(Long::parseLong).collect(Collectors.toList());

    List<Long> possibleSetMembers = allNumbers.subList(0, allNumbers.indexOf(invalidNumber));

    for(int i = 0; i < possibleSetMembers.size(); i++) {
      List<Long> manipulatedList = new ArrayList<>(possibleSetMembers.subList(i, possibleSetMembers.size()));

      Set<Long> result = tryToFindSetSummingUpToSearched(invalidNumber, manipulatedList);

      if(result != null) {
        List<Long> sortedResult = result.stream().sorted().collect(Collectors.toList());
        return sortedResult.get(0) + sortedResult.get(result.size() - 1);
      }
    }

    System.err.println("There was no Set which Sum was equal to the invalid Number");
    return -1;
  }

  /**
   * Tries to find a Set that Sum is equal to the searched Number
   *
   * @param searched      Number that was searched
   * @param possibilities List of Possible Longs
   *
   * @return Set of Long if the Sum of it is equal to searched or null
   */
  private Set<Long> tryToFindSetSummingUpToSearched(final Long searched, final List<Long> possibilities) {
    Long sum = 0L;

    for(int i = 0; i < possibilities.size() && sum < searched; i++) {
      sum += possibilities.get(i);

      if(sum.equals(searched)) {
        return new HashSet<>(possibilities.subList(0, i + 1));
      }
    }

    return null;
  }
}
