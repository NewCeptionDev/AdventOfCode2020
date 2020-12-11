package me.newceptiondev.day11;

import java.util.Arrays;
import java.util.List;

public class SeatingArea {

    private char[][] seats;

    private static final char OCCUPIED = '#';
    private static final char EMPTY = 'L';
    private static final char FLOOR = '.';

    public SeatingArea(List<String> description) {
        int height = description.size();
        int width = description.get(0).length();

        seats = new char[height][width];

        for (int i = 0; i < description.size(); i++) {
            seats[i] = description.get(i).toCharArray();
        }
    }

    public boolean updateSeatingAreaForTask1() {
        char[][] newSeatOccupancy = new char[seats.length][seats[0].length];

        for (int y = 0; y < seats.length; y++) {
            for (int x = 0; x < seats[y].length; x++) {
                if (seats[y][x] == FLOOR) {
                    newSeatOccupancy[y][x] = FLOOR;
                } else {

                    int occupiedSurroundingSeats = getOccupiedSurroundingSeatsForTask1(x, y);

                    if (occupiedSurroundingSeats == 0) {
                        newSeatOccupancy[y][x] = OCCUPIED;
                    } else if (occupiedSurroundingSeats >= 4) {
                        newSeatOccupancy[y][x] = EMPTY;
                    } else {
                        newSeatOccupancy[y][x] = seats[y][x];
                    }
                }
            }
        }

        boolean same = Arrays.deepEquals(seats, newSeatOccupancy);

        this.seats = newSeatOccupancy;

        return same;
    }

    public boolean updateSeatingAreaForTask2() {
        char[][] newSeatOccupancy = new char[seats.length][seats[0].length];

        for (int y = 0; y < seats.length; y++) {
            for (int x = 0; x < seats[y].length; x++) {
                if (seats[y][x] == FLOOR) {
                    newSeatOccupancy[y][x] = FLOOR;
                } else {

                    int occupiedSurroundingSeats = getOccupiedSurroundingSeatsForTask2(x, y);

                    if (occupiedSurroundingSeats == 0) {
                        newSeatOccupancy[y][x] = OCCUPIED;
                    } else if (occupiedSurroundingSeats >= 5) {
                        newSeatOccupancy[y][x] = EMPTY;
                    } else {
                        newSeatOccupancy[y][x] = seats[y][x];
                    }
                }
            }
        }

        boolean same = Arrays.deepEquals(seats, newSeatOccupancy);

        this.seats = newSeatOccupancy;

        return same;
    }

    private int getOccupiedSurroundingSeatsForTask1(int x, int y) {
        int occupied = 0;

        if (x > 0) {
            if (seats[y][x - 1] == OCCUPIED) {
                occupied++;
            }

            if (y > 0 && seats[y - 1][x - 1] == OCCUPIED) {
                occupied++;
            }

            if (y < seats.length - 1 && seats[y + 1][x - 1] == OCCUPIED) {
                occupied++;
            }
        }

        if (x < seats[y].length - 1) {
            if (seats[y][x + 1] == OCCUPIED) {
                occupied++;
            }

            if (y > 0 && seats[y - 1][x + 1] == OCCUPIED) {
                occupied++;
            }

            if (y < seats.length - 1 && seats[y + 1][x + 1] == OCCUPIED) {
                occupied++;
            }
        }

        if (y > 0 && seats[y - 1][x] == OCCUPIED) {
            occupied++;
        }

        if (y < seats.length - 1 && seats[y + 1][x] == OCCUPIED) {
            occupied++;
        }

        return occupied;
    }

    private int getOccupiedSurroundingSeatsForTask2(int x, int y) {
        int occupied = 0;

        if(isNextVisibleSeatOccupied(x + 1, y, 1, 0)){
            occupied++;
        }

        if(isNextVisibleSeatOccupied(x - 1, y, -1, 0)){
            occupied++;
        }

        if(isNextVisibleSeatOccupied(x, y + 1, 0, 1)){
            occupied++;
        }

        if(isNextVisibleSeatOccupied(x , y - 1, 0, -1)){
            occupied++;
        }

        if(isNextVisibleSeatOccupied(x + 1, y + 1, 1, 1)){
            occupied++;
        }

        if(isNextVisibleSeatOccupied(x + 1, y - 1, 1, -1)){
            occupied++;
        }

        if(isNextVisibleSeatOccupied(x - 1, y - 1, -1, -1)){
            occupied++;
        }

        if(isNextVisibleSeatOccupied(x - 1, y + 1, -1, 1)){
            occupied++;
        }

        return occupied;
    }

    private boolean isNextVisibleSeatOccupied(int x, int y, int xMovement, int yMovement) {
        if (y >= 0 && y < seats.length && x >= 0 && x < seats[y].length) {
            if (seats[y][x] == OCCUPIED) {
                return true;
            } else if(seats[y][x] == FLOOR){
                return isNextVisibleSeatOccupied(x + xMovement, y + yMovement, xMovement, yMovement);
            }
        }
        return false;
    }

    public int getOccupiedSeatsCount() {
        int occupiedSeats = 0;

        for (char[] seat : seats) {
            for (char c : seat) {
                if (c == OCCUPIED) {
                    occupiedSeats++;
                }
            }
        }

        return occupiedSeats;
    }
}
