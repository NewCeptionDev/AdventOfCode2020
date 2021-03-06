package me.newceptiondev.day5;

import me.newceptiondev.util.FileUtil;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class Day5 {

  private static final String FILE_NAME = "day5Task1Input";

  public static void main(String[] args) {
    List<String> inputs = FileUtil.readFileAsListOfLines(FILE_NAME);

    new Day5(inputs);
  }

  private Day5(List<String> inputs) {
    System.out.println("Task 1: " + task1(inputs));
    System.out.println("Task 2: " + task2(inputs));
  }

  /**
   * Task 1
   *
   * @param inputs List of String
   *
   * @return Highest Seat ID
   */
  public int task1(final List<String> inputs) {
    List<Seat> takenSeats = parseInputsToSeats(inputs);

    int highestSeatId = 0;

    for(Seat seat : takenSeats) {
      int seatId = seat.calculateSeatId();

      if(seatId > highestSeatId) {
        highestSeatId = seatId;
      }
    }

    return highestSeatId;
  }

  /**
   * Task 2
   *
   * @param inputs List of String
   *
   * @return The SeatId of the searched Seat
   */
  public int task2(final List<String> inputs) {
    List<Seat> takenSeats = parseInputsToSeats(inputs);

    Set<Integer> idsFromTakenSeats = calculateSeatIdsForAllSeats(takenSeats);
    Map<Integer, Set<Seat>> seatsPerRow = sortSeatsToRows(takenSeats);

    Set<Seat> missingSeats = getMissingSeats(seatsPerRow);

    for(Seat seat : missingSeats) {
      int seatId = seat.calculateSeatId();

      //Checks for a SeatId where the SeatIds +1 and -1 are present
      if(idsFromTakenSeats.contains(seatId - 1) && idsFromTakenSeats.contains(seatId + 1)) {
        return seatId;
      }
    }

    System.err.println("There was no Id that matches the Requirements");
    return -1;
  }

  /**
   * Parses the Input to Seats
   *
   * @param inputs List of String
   *
   * @return List of Seat
   */
  private List<Seat> parseInputsToSeats(final List<String> inputs) {
    return inputs.stream().map(Seat::new).collect(Collectors.toList());
  }

  /**
   * Returns a Set with the SeatIds for all taken Seats
   *
   * @param takenSeats List of Seat
   *
   * @return Set of Integer
   */
  private Set<Integer> calculateSeatIdsForAllSeats(final List<Seat> takenSeats) {
    return takenSeats.stream().map(Seat::calculateSeatId).collect(Collectors.toSet());
  }

  /**
   * Sorts the Seats by their Row
   *
   * @param takenSeats List of Seat
   *
   * @return Map of Integer and Set of Seat
   */
  private Map<Integer, Set<Seat>> sortSeatsToRows(final List<Seat> takenSeats) {
    Map<Integer, Set<Seat>> seatsByRow = new HashMap<>();

    for(Seat seat : takenSeats) {
      if(seatsByRow.containsKey(seat.getRow())) {
        seatsByRow.get(seat.getRow()).add(seat);
      } else {
        Set<Seat> seatRow = new HashSet<>();
        seatRow.add(seat);

        seatsByRow.put(seat.getRow(), seatRow);
      }
    }

    return seatsByRow;
  }

  /**
   * Returns all Missing Seats from the given Rows
   *
   * @param seatsPerRow Set of Seats
   *
   * @return Set of missing Seats
   */
  private Set<Seat> getMissingSeats(final Map<Integer, Set<Seat>> seatsPerRow) {
    Set<Seat> missingSeats = new HashSet<>();

    for(Integer row : seatsPerRow.keySet()) {
      if(seatsPerRow.get(row).size() != 8) {
        missingSeats.addAll(createMissingSeats(seatsPerRow.get(row)));
      }
    }

    return missingSeats;
  }

  /**
   * Creates all missing Seats from the Row
   *
   * @param takenSeats Set with all taken Seats in this Row
   *
   * @return Set with the missing Seats from this Row
   */
  private Set<Seat> createMissingSeats(final Set<Seat> takenSeats) {
    Set<Integer> fullColumn = IntStream.rangeClosed(0, 7).boxed().collect(Collectors.toSet());
    int row = -1;

    for(Seat seat : takenSeats) {
      if(row == -1) {
        row = seat.getRow();
      }

      fullColumn.remove(seat.getColumn());
    }

    final int finalRow = row;

    return fullColumn.stream().map(missingColumn -> new Seat(finalRow, missingColumn)).collect(Collectors.toSet());
  }
}
