package me.newceptiondev.day11;

import java.util.Arrays;
import java.util.List;

public class SeatingArea {

  private static final char OCCUPIED = '#';
  private static final char EMPTY = 'L';
  private static final char FLOOR = '.';
  private char[][] seats;

  public SeatingArea(List<String> description) {
    int height = description.size();
    int width = description.get(0).length();

    seats = new char[height][width];

    for(int i = 0; i < description.size(); i++) {
      seats[i] = description.get(i).toCharArray();
    }
  }

  public boolean updateSeatingAreaForTask1() {
    char[][] newSeatOccupancy = new char[seats.length][seats[0].length];

    for(int y = 0; y < seats.length; y++) {
      for(int x = 0; x < seats[y].length; x++) {
        if(seats[y][x] == FLOOR) {
          newSeatOccupancy[y][x] = FLOOR;
        } else {

          int occupiedSurroundingSeats = getOccupiedSurroundingSeatsForTask1(x, y);

          if(occupiedSurroundingSeats == 0) {
            newSeatOccupancy[y][x] = OCCUPIED;
          } else if(occupiedSurroundingSeats >= 4) {
            newSeatOccupancy[y][x] = EMPTY;
          } else {
            newSeatOccupancy[y][x] = seats[y][x];
          }
        }
      }
    }

    boolean different = !Arrays.deepEquals(seats, newSeatOccupancy);

    seats = newSeatOccupancy;

    return different;
  }

  /**
   * Gets Count of Occupied Seats surrounding the given Position by Requirements of Task 1
   *
   * @param horizontal Horizontal Position
   * @param vertical   Vertical Position
   *
   * @return Count of Occupied Seats
   */
  private int getOccupiedSurroundingSeatsForTask1(int horizontal, int vertical) {
    int occupied = 0;

    occupied += getOccupiedSeatsWesternOfPosition(horizontal, vertical);

    occupied += getOccupiedSeatsEasternOfPosition(horizontal, vertical);

    if(vertical > 0 && seats[vertical - 1][horizontal] == OCCUPIED) {
      occupied++;
    }

    if(vertical < seats.length - 1 && seats[vertical + 1][horizontal] == OCCUPIED) {
      occupied++;
    }

    return occupied;
  }

  /**
   * Counts the Occupied Seats eastern of the given Position
   *
   * @param horizontal Horizontal Position
   * @param vertical   Vertical Position
   *
   * @return Count of Occupied Seats
   */
  private int getOccupiedSeatsEasternOfPosition(final int horizontal, final int vertical) {
    int occupied = 0;

    if(horizontal < seats[vertical].length - 1) {
      if(seats[vertical][horizontal + 1] == OCCUPIED) {
        occupied++;
      }

      if(vertical > 0 && seats[vertical - 1][horizontal + 1] == OCCUPIED) {
        occupied++;
      }

      if(vertical < seats.length - 1 && seats[vertical + 1][horizontal + 1] == OCCUPIED) {
        occupied++;
      }
    }
    return occupied;
  }

  /**
   * Counts the Occupied Seats western of the given Position
   *
   * @param horizontal Horizontal Position
   * @param vertical   Vertical Position
   *
   * @return Count of Occupied Seats
   */
  private int getOccupiedSeatsWesternOfPosition(final int horizontal, final int vertical) {
    int occupied = 0;

    if(horizontal > 0) {
      if(seats[vertical][horizontal - 1] == OCCUPIED) {
        occupied++;
      }

      if(vertical > 0 && seats[vertical - 1][horizontal - 1] == OCCUPIED) {
        occupied++;
      }

      if(vertical < seats.length - 1 && seats[vertical + 1][horizontal - 1] == OCCUPIED) {
        occupied++;
      }
    }
    return occupied;
  }

  /**
   * Updates the Seating Area by the Requirements of Task 2
   *
   * @return If the new Seat Occupation is different to the one before
   */
  public boolean updateSeatingAreaForTask2() {
    char[][] newSeatOccupancy = new char[seats.length][seats[0].length];

    for(int y = 0; y < seats.length; y++) {
      for(int x = 0; x < seats[y].length; x++) {
        if(seats[y][x] == FLOOR) {
          newSeatOccupancy[y][x] = FLOOR;
        } else {

          int occupiedSurroundingSeats = getOccupiedSurroundingSeatsForTask2(x, y);

          if(occupiedSurroundingSeats == 0) {
            newSeatOccupancy[y][x] = OCCUPIED;
          } else if(occupiedSurroundingSeats >= 5) {
            newSeatOccupancy[y][x] = EMPTY;
          } else {
            newSeatOccupancy[y][x] = seats[y][x];
          }
        }
      }
    }

    boolean different = !Arrays.deepEquals(seats, newSeatOccupancy);

    seats = newSeatOccupancy;

    return different;
  }

  /**
   * Returns the Count of Occupied Seats surrounding the given Seats by the Requirements of Task 2
   *
   * @param horizontal Horizontal Position
   * @param vertical   Vertical Position
   *
   * @return Count of Occupied Seats
   */
  private int getOccupiedSurroundingSeatsForTask2(int horizontal, int vertical) {
    int occupied = 0;

    if(isNextVisibleSeatOccupied(horizontal + 1, vertical, 1, 0)) {
      occupied++;
    }

    if(isNextVisibleSeatOccupied(horizontal - 1, vertical, -1, 0)) {
      occupied++;
    }

    if(isNextVisibleSeatOccupied(horizontal, vertical + 1, 0, 1)) {
      occupied++;
    }

    if(isNextVisibleSeatOccupied(horizontal, vertical - 1, 0, -1)) {
      occupied++;
    }

    if(isNextVisibleSeatOccupied(horizontal + 1, vertical + 1, 1, 1)) {
      occupied++;
    }

    if(isNextVisibleSeatOccupied(horizontal + 1, vertical - 1, 1, -1)) {
      occupied++;
    }

    if(isNextVisibleSeatOccupied(horizontal - 1, vertical - 1, -1, -1)) {
      occupied++;
    }

    if(isNextVisibleSeatOccupied(horizontal - 1, vertical + 1, -1, 1)) {
      occupied++;
    }

    return occupied;
  }

  /**
   * Checks if the next Visible Seat is Occupied
   *
   * @param horizontal         Horizontal Position
   * @param vertical           Vertical Position
   * @param horizontalMovement Horizontal Movement
   * @param verticalMovement   Vertical Movement
   *
   * @return True if the next Visible Seat is Occupied, False if it is not Occupied
   */
  private boolean isNextVisibleSeatOccupied(int horizontal, int vertical, int horizontalMovement,
                                            int verticalMovement) {
    if(vertical >= 0 && vertical < seats.length && horizontal >= 0 && horizontal < seats[vertical].length) {
      if(seats[vertical][horizontal] == OCCUPIED) {
        return true;
      } else if(seats[vertical][horizontal] == FLOOR) {
        return isNextVisibleSeatOccupied(horizontal + horizontalMovement, vertical + verticalMovement,
                                         horizontalMovement, verticalMovement);
      }
    }
    return false;
  }

  /**
   * Returns the Count of all Occupied Seats
   *
   * @return Count of all Occupied Seats
   */
  public int getOccupiedSeatsCount() {
    int occupiedSeats = 0;

    for(char[] seat : seats) {
      occupiedSeats = getOccupiedSeatsWithinRow(seat);
    }

    return occupiedSeats;
  }

  /**
   * Returns the Count of Occupied Seats in the given Row
   *
   * @param row Char Array Row
   *
   * @return Count of Occupied Seats
   */
  private int getOccupiedSeatsWithinRow(final char[] row) {
    int occupied = 0;

    for(char seat : row) {
      if(seat == OCCUPIED) {
        occupied++;
      }
    }
    return occupied;
  }
}
