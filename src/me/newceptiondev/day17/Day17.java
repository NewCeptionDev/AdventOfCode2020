package me.newceptiondev.day17;

import me.newceptiondev.util.FileUtil;

import java.util.List;

public class Day17 {

    public static final String fileName = "day17Task1Input";
    public static final char ACTIVE = '#';
    public static final char INACTIVE = '.';
    public static final int STEPS = 6;

    public static void main(String[] args) {
        List<String> inputs = FileUtil.readFileAsListOfLines(fileName);

        new Day17(inputs);
    }

    public Day17() {
    }

    public Day17(List<String> inputs) {
        System.out.println("Task 1: " + task1(inputs));
        System.out.println("Task 2: " + task2(inputs));
    }

    /**
     * Task 1
     *
     * @param inputs List of String
     * @return Count of Active Elements after 6 Steps
     */
    public int task1(final List<String> inputs) {
        char[][][] layers = new char[1][inputs.size()][inputs.get(0).length()];

        for (int y = 0; y < inputs.size(); y++) {
            layers[0][y] = inputs.get(y).toCharArray();
        }

        for (int i = 0; i < STEPS; i++) {
            layers = calculateNextStateForTask1(layers);
        }

        return countActiveElements(layers);
    }

    /**
     * Task 2
     *
     * @param inputs List of String
     * @return Count of Active Elements after 6 Steps
     */
    public int task2(final List<String> inputs) {
        char[][][][] layers = new char[1][1][inputs.size()][inputs.get(0).length()];

        for (int y = 0; y < inputs.size(); y++) {
            layers[0][0][y] = inputs.get(y).toCharArray();
        }

        for (int i = 0; i < 6; i++) {
            layers = calculateNextStateForTask2(layers);
        }

        int activeElements = 0;

        for (char[][][] layer : layers) {
            activeElements += countActiveElements(layer);
        }

        return activeElements;
    }

    /**
     * Calculates the next Step for a 3-Dimensional Array
     *
     * @param currentState Current Step
     * @return Next Step
     */
    private char[][][] calculateNextStateForTask1(final char[][][] currentState) {
        char[][][] newState = new char[currentState.length + 2][currentState[0].length + 2][currentState[0][0].length + 2];

        for (int z = 0; z < newState.length; z++) {
            for (int y = 0; y < newState[z].length; y++) {
                for (int x = 0; x < newState[z][y].length; x++) {
                    newState[z][y][x] = calculateStateForElementForTask1(z, y, x, currentState);
                }
            }
        }

        return newState;
    }

    /**
     * Calculates the next Step for a 4-Dimensional Array
     *
     * @param currentState Current Step
     * @return Next Step
     */
    private char[][][][] calculateNextStateForTask2(final char[][][][] currentState) {
        char[][][][] newState = new char[currentState.length + 2][currentState[0].length + 2][currentState[0][0].length + 2][currentState[0][0][0].length + 2];

        for (int z = 0; z < newState.length; z++) {
            for (int y = 0; y < newState[z].length; y++) {
                for (int x = 0; x < newState[z][y].length; x++) {
                    for(int w = 0; w < newState[z][y][x].length; w++) {
                        newState[z][y][x][w] = calculateStateForElementForTask2(z, y, x, w, currentState);
                    }
                }
            }
        }

        return newState;
    }

    /**
     * Calculates the next State for an Element within a 3-Dimensional Array
     *
     * @param z Z-Index
     * @param y Y-Index
     * @param x X-Index
     * @param lastState  Last State
     * @return Char representing State of Element
     */
    private char calculateStateForElementForTask1(final int z, final int y, final int x, final char[][][] lastState) {
        int activeNeighbours = 0;
        char lastStateOfElement = '.';

        for (int possibleZ = z - 1; possibleZ <= z + 1; possibleZ++) {
            for (int possibleY = y - 1; possibleY <= y + 1; possibleY++) {
                for (int possibleX = x - 1; possibleX <= x + 1; possibleX++) {
                    if (possibleZ > 0 && possibleZ <= lastState.length && possibleY > 0 && possibleY <= lastState[possibleZ - 1].length && possibleX > 0 && possibleX <= lastState[possibleZ - 1][possibleY - 1].length && lastState[possibleZ - 1][possibleY - 1][possibleX - 1] == ACTIVE) {
                        if (possibleZ != z || possibleY != y || possibleX != x) {
                            activeNeighbours++;
                        } else {
                            lastStateOfElement = '#';
                        }
                    }
                }
            }
        }

        if (lastStateOfElement == ACTIVE && (activeNeighbours == 2 || activeNeighbours == 3)) {
            return ACTIVE;
        } else if (lastStateOfElement == INACTIVE && activeNeighbours == 3) {
            return ACTIVE;
        }

        return INACTIVE;
    }

    /**
     * Calculates the next State for an Element within a 4-Dimensional Array
     *
     * @param z Z-Index
     * @param y Y-Index
     * @param x X-Index
     * @param w W-Index
     * @param lastState Last State
     * @return Char representing State of Element
     */
    private char calculateStateForElementForTask2(final int z, final int y, final int x, final int w, final char[][][][] lastState) {
        int activeNeighbours = 0;
        char lastStateOfElement = '.';

        for (int possibleZ = z - 1; possibleZ <= z + 1; possibleZ++) {
            for (int possibleY = y - 1; possibleY <= y + 1; possibleY++) {
                for (int possibleX = x - 1; possibleX <= x + 1; possibleX++) {
                    for(int possibleW = w - 1; possibleW <= w + 1; possibleW++) {
                        if (possibleZ > 0 && possibleZ <= lastState.length && possibleY > 0 && possibleY <= lastState[possibleZ - 1].length && possibleX > 0 && possibleX <= lastState[possibleZ - 1][possibleY - 1].length && possibleW > 0 && possibleW <= lastState[possibleZ - 1][possibleY - 1][possibleX - 1].length && lastState[possibleZ - 1][possibleY - 1][possibleX - 1][possibleW - 1] == ACTIVE) {
                            if (possibleZ != z || possibleY != y || possibleX != x || possibleW != w) {
                                activeNeighbours++;
                            } else {
                                lastStateOfElement = '#';
                            }
                        }
                    }
                }
            }
        }

        if (lastStateOfElement == ACTIVE && (activeNeighbours == 2 || activeNeighbours == 3)) {
            return ACTIVE;
        } else if (lastStateOfElement == INACTIVE && activeNeighbours == 3) {
            return ACTIVE;
        }

        return INACTIVE;
    }

    /**
     * Counts the active Elements within a 3-Dimensional Array
     *
     * @param layers Array
     * @return Count of active Elements
     */
    private int countActiveElements(final char[][][] layers) {
        int activeElements = 0;

        for (char[][] layer : layers) {
            for (char[] chars : layer) {
                for (char aChar : chars) {
                    if (aChar == ACTIVE) {
                        activeElements++;
                    }
                }
            }
        }
        return activeElements;
    }
}
