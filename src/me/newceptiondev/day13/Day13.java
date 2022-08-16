package me.newceptiondev.day13;

import me.newceptiondev.util.FileUtil;
import me.newceptiondev.util.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Day13 {

  private static final String FILE_NAME = "day13Task1Input";

  public static void main(String[] args) {
    List<String> inputs = FileUtil.readFileAsListOfLines(FILE_NAME);

    new Day13(inputs);
  }

  public Day13() {
  }

  private Day13(List<String> inputs) {
    System.out.println("Task 1: " + task1(inputs));
    System.out.println("Task 2: " + task2(inputs));
  }

  /**
   * Task 1
   *
   * @param inputs List of String
   *
   * @return Product of Bus Id and Minutes of Wait
   */
  public int task1(List<String> inputs) {
    int timeToAirport = Integer.parseInt(inputs.get(0));
    List<Integer> busIds = parseBusIds(inputs.get(1));

    Tuple<Integer, Integer> earliestBus = null;

    for(Integer busId : busIds) {
      if(busId != null) {
        int waitingTime = busId - (timeToAirport % busId);

        if(earliestBus == null || earliestBus.getY() > waitingTime) {
          earliestBus = new Tuple<>(busId, waitingTime);
        }
      }
    }

    if(earliestBus != null) {
      return earliestBus.getX() * earliestBus.getY();
    }

    System.err.println("There was no valid BusId within the Input");
    return -1;
  }

  private List<Integer> parseBusIds(String input) {
    List<Integer> parsedBusIds = new ArrayList<>();
    String[] busIds = input.split(",");

    for(String id : busIds) {
      if(id.equals("x")) {
        parsedBusIds.add(null);
      } else {
        parsedBusIds.add(Integer.parseInt(id));
      }
    }

    return parsedBusIds;
  }

  /**
   * Task 2
   *
   * @param inputs List of String
   *
   * @return Earliest Timestamp that matches Requirements of Task 2
   */
  public Long task2(List<String> inputs) {
    List<Integer> busIds = parseBusIds(inputs.get(1));

    List<Tuple<Integer, Integer>> idsWithIndex =
        busIds.stream().map(integer -> new Tuple<>(integer, busIds.indexOf(integer)))
              .filter(integerIntegerTuple -> integerIntegerTuple.getX() != null).collect(Collectors.toList());

    long additive = idsWithIndex.get(0).getX();
    long lowestMatchingTimestamp = idsWithIndex.get(0).getX();

    for(int i = 1; i < idsWithIndex.size(); i++) {
      List<Tuple<Integer, Integer>> currentSublist = idsWithIndex.subList(0, i + 1);

      while(!allBusMatch(currentSublist, lowestMatchingTimestamp)) {
          lowestMatchingTimestamp += additive;
      }
      additive = currentSublist.stream().map(Tuple::getX).map(elem -> (long) elem).reduce(1L, (l1, l2) -> l1 * l2);
    }

    return lowestMatchingTimestamp;
  }

  private boolean allBusMatch(List<Tuple<Integer, Integer>> busIds, long toCheck) {
    return busIds.stream().allMatch(elem -> (toCheck + elem.getY()) % elem.getX() == 0);
  }
}
