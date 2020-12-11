package me.newceptiondev.day11;

import me.newceptiondev.util.FileUtil;

import java.util.List;

public class Day11 {

    public static final String fileName = "day11Task1Input";

    public static void main(String[] args) {
        List<String> inputs = FileUtil.readFileAsListOfLines(fileName);

        new Day11(inputs);
    }

    public Day11() {
    }

    public Day11(List<String> inputs) {
        System.out.println("Task 1: " + task1(inputs));
        System.out.println("Task 2: " + task2(inputs));
    }

    /**
     * Task 1
     *
     * @param inputs List of String
     * @return Count of Occupied Seats after the Chaos stabilized
     */
    public int task1(List<String> inputs) {
        SeatingArea area = new SeatingArea(inputs);

        boolean stabilized = area.updateSeatingAreaForTask1();

        while(!stabilized){
            stabilized = area.updateSeatingAreaForTask1();
        }

        return area.getOccupiedSeatsCount();
    }

    /**
     * Task 2
     *
     * @param inputs List of String
     * @return
     */
    public int task2(List<String> inputs) {
        SeatingArea area = new SeatingArea(inputs);

        boolean stabilized = area.updateSeatingAreaForTask2();

        while(!stabilized){
            stabilized = area.updateSeatingAreaForTask2();
        }

        return area.getOccupiedSeatsCount();
    }

}
