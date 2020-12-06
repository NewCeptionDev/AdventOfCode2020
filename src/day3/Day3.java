package day3;

import util.FileUtil;

import java.util.List;

public class Day3 {

    public static final String fileName = "day3Task1Input";
    private final char TREE = '#';

    public static void main(String[] args) {
        new Day3();
    }

    public Day3() {
        List<String> inputs = FileUtil.readFileAsListOfLines(fileName);

        System.out.println("Task 1: " + task1(inputs));
        System.out.println("Task 2: " + task2(inputs));
    }

    /**
     * Task 1
     *
     * @param inputs List of String
     * @return Number of Encounters with Trees when moving 3 on horizontal and 1 on vertical Axis
     */
    public long task1(final List<String> inputs) {
        return getEncounters(inputs, 3, 1);
    }

    /**
     * Task 2
     *
     * @param inputs List of String
     * @return Multiplication of Encounters with Trees on 5 different Slopes
     */
    public long task2(final List<String> inputs) {
        long firstSlopeEncounters = getEncounters(inputs, 1, 1);
        long secondSlopeEncounters = getEncounters(inputs, 3, 1);
        long thirdSlopeEncounters = getEncounters(inputs, 5, 1);
        long fourthSlopeEncounters = getEncounters(inputs, 7, 1);
        long fifthSlopeEncounters = getEncounters(inputs, 1, 2);

        return firstSlopeEncounters * secondSlopeEncounters * thirdSlopeEncounters * fourthSlopeEncounters * fifthSlopeEncounters;
    }

    /**
     * Counts the Number of Encounters with a Tree
     *
     * @param inputs List of String
     * @param horizontalMovement The Movement on the horizontal Axis
     * @param verticalMovement The Movement on the vertical Axis
     * @return Number of Encounters with Trees
     */
    private long getEncounters(final List<String> inputs, final int horizontalMovement, final int verticalMovement) {
        final int partWidth = inputs.get(0).length();
        final int height = inputs.size();

        long encounteredTrees = 0;
        int currentX = 0;
        int currentY = 0;

        //Run as long as the Bottom is not reached
        while (currentY < height) {
            //Calculates the next Positions - Takes the maximum Width of the Input into account
            int nextX = currentX + horizontalMovement < partWidth ? currentX + horizontalMovement : currentX + horizontalMovement - partWidth;
            int nextY = currentY + verticalMovement;

            //Safety check to ensure that nextY will not produce an IndexOutOfBounds Exception
            if (nextY < height) {
                char location = inputs.get(nextY).charAt(nextX);

                if (location == TREE) {
                    encounteredTrees++;
                }
            }
            currentX = nextX;
            currentY = nextY;
        }

        return encounteredTrees;
    }

}
