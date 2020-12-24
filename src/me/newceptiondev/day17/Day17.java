package me.newceptiondev.day17;

import me.newceptiondev.util.FileUtil;

import java.util.List;

public class Day17 {

  private static final String FILE_NAME = "day17Task1Input";
  private static final char ACTIVE = '#';
  private static final char INACTIVE = '.';
  private static final int STEPS = 6;

  public static void main(String[] args) {
    List<String> inputs = FileUtil.readFileAsListOfLines(FILE_NAME);

    new Day17(inputs);
  }

  public Day17() {
  }

  private Day17(List<String> inputs) {
    System.out.println("Task 1: " + task1(inputs));
    System.out.println("Task 2: " + task2(inputs));
  }

  /**
   * Task 1
   *
   * @param inputs List of String
   *
   * @return Count of Active Elements after 6 Steps
   */
  public int task1(final List<String> inputs) {
    char[][][] layers = new char[1][inputs.size()][inputs.get(0).length()];

    for(int y = 0; y < inputs.size(); y++) {
      layers[0][y] = inputs.get(y).toCharArray();
    }

    for(int i = 0; i < STEPS; i++) {
      layers = calculateNextStateForTask1(layers);
    }

    return countActiveElements(layers);
  }

  /**
   * Calculates the next Step for a 3-Dimensional Array
   *
   * @param currentState Current Step
   *
   * @return Next Step
   */
  private char[][][] calculateNextStateForTask1(final char[][][] currentState) {
    char[][][] newState = new char[currentState.length + 2][currentState[0].length + 2][currentState[0][0].length + 2];

    for(int z = 0; z < newState.length; z++) {
      for(int y = 0; y < newState[z].length; y++) {
        for(int x = 0; x < newState[z][y].length; x++) {
          newState[z][y][x] = calculateStateForElementForTask1(z, y, x, currentState);
        }
      }
    }

    return newState;
  }

  /**
   * Calculates the next State for an Element within a 3-Dimensional Array
   *
   * @param zIndex    Z-Index
   * @param yIndex    Y-Index
   * @param xIndex    X-Index
   * @param lastState Last State
   *
   * @return Char representing State of Element
   */
  private char calculateStateForElementForTask1(final int zIndex, final int yIndex, final int xIndex,
                                                final char[][][] lastState) {
    int activeNeighbours = 0;
    char lastStateOfElement = '.';

    for(int possibleZ = zIndex - 1; possibleZ <= zIndex + 1; possibleZ++) {
      for(int possibleY = yIndex - 1; possibleY <= yIndex + 1; possibleY++) {
        for(int possibleX = xIndex - 1; possibleX <= xIndex + 1; possibleX++) {
          if(isPossiblePositionWithin3DArray(lastState, possibleZ, possibleY, possibleX) &&
             lastState[possibleZ - 1][possibleY - 1][possibleX - 1] == ACTIVE) {
            final boolean isNeighbourPosition = possibleZ != zIndex || possibleY != yIndex || possibleX != xIndex;
            if(isNeighbourPosition) {
              activeNeighbours++;
            } else {
              lastStateOfElement = '#';
            }
          }
        }
      }
    }

    if(lastStateOfElement == ACTIVE && (activeNeighbours == 2 || activeNeighbours == 3)) {
      return ACTIVE;
    } else if(lastStateOfElement == INACTIVE && activeNeighbours == 3) {
      return ACTIVE;
    }

    return INACTIVE;
  }

  /**
   * Checks if the Possible Position is within the Arrays Boundaries
   *
   * @param array     Array
   * @param possibleZ Possible Z Value
   * @param possibleY Possible Y Value
   * @param possibleX Possible X Value
   *
   * @return True if all Values are Within the Arrays Boundaries
   */
  private boolean isPossiblePositionWithin3DArray(final char[][][] array, final int possibleZ, final int possibleY,
                                                  final int possibleX) {
    return possibleZ > 0 && possibleZ <= array.length && possibleY > 0 && possibleY <= array[possibleZ - 1].length &&
           possibleX > 0 && possibleX <= array[possibleZ - 1][possibleY - 1].length;
  }

  /**
   * Task 2
   *
   * @param inputs List of String
   *
   * @return Count of Active Elements after 6 Steps
   */
  public int task2(final List<String> inputs) {
    char[][][][] layers = new char[1][1][inputs.size()][inputs.get(0).length()];

    for(int y = 0; y < inputs.size(); y++) {
      layers[0][0][y] = inputs.get(y).toCharArray();
    }

    for(int i = 0; i < 6; i++) {
      layers = calculateNextStateForTask2(layers);
    }

    int activeElements = 0;

    for(char[][][] layer : layers) {
      activeElements += countActiveElements(layer);
    }

    return activeElements;
  }

  /**
   * Counts the active Elements within a 3-Dimensional Array
   *
   * @param layers Array
   *
   * @return Count of active Elements
   */
  private int countActiveElements(final char[][][] layers) {
    int activeElements = 0;

    for(char[][] layer : layers) {
      for(char[] chars : layer) {
        for(char aChar : chars) {
          if(aChar == ACTIVE) {
            activeElements++;
          }
        }
      }
    }
    return activeElements;
  }

  /**
   * Calculates the next Step for a 4-Dimensional Array
   *
   * @param currentState Current Step
   *
   * @return Next Step
   */
  private char[][][][] calculateNextStateForTask2(final char[][][][] currentState) {
    char[][][][] newState =
        new char[currentState.length + 2][currentState[0].length + 2][currentState[0][0].length + 2][
            currentState[0][0][0].length + 2];

    for(int z = 0; z < newState.length; z++) {
      for(int y = 0; y < newState[z].length; y++) {
        for(int x = 0; x < newState[z][y].length; x++) {
          for(int w = 0; w < newState[z][y][x].length; w++) {
            newState[z][y][x][w] = calculateStateForElementForTask2(z, y, x, w, currentState);
          }
        }
      }
    }

    return newState;
  }

  /**
   * Calculates the next State for an Element within a 4-Dimensional Array
   *
   * @param z         Z-Index
   * @param y         Y-Index
   * @param x         X-Index
   * @param w         W-Index
   * @param lastState Last State
   *
   * @return Char representing State of Element
   */
  private char calculateStateForElementForTask2(final int z, final int y, final int x, final int w,
                                                final char[][][][] lastState) {
    int activeNeighbours = 0;
    char lastStateOfElement = '.';

    for(int possibleZ = z - 1; possibleZ <= z + 1; possibleZ++) {
      for(int possibleY = y - 1; possibleY <= y + 1; possibleY++) {
        for(int possibleX = x - 1; possibleX <= x + 1; possibleX++) {
          for(int possibleW = w - 1; possibleW <= w + 1; possibleW++) {
            if(isPossiblePositionWithin4DArray(lastState, possibleZ, possibleY, possibleX, possibleW) &&
               lastState[possibleZ - 1][possibleY - 1][possibleX - 1][possibleW - 1] == ACTIVE) {
              final boolean isNeighbourPosition = possibleZ != z || possibleY != y || possibleX != x || possibleW != w;
              if(isNeighbourPosition) {
                activeNeighbours++;
              } else {
                lastStateOfElement = '#';
              }
            }
          }
        }
      }
    }

    if(lastStateOfElement == ACTIVE && (activeNeighbours == 2 || activeNeighbours == 3)) {
      return ACTIVE;
    } else if(lastStateOfElement == INACTIVE && activeNeighbours == 3) {
      return ACTIVE;
    }

    return INACTIVE;
  }

  /**
   * Checks if the Possible Position is within the Arrays Boundaries
   *
   * @param array     Array
   * @param possibleZ Possible Z Value
   * @param possibleY Possible Y Value
   * @param possibleX Possible X Value
   * @param possibleW Possible W Value
   *
   * @return True if all Values are Within the Arrays Boundaries
   */
  private boolean isPossiblePositionWithin4DArray(final char[][][][] array, final int possibleZ, final int possibleY,
                                                  final int possibleX, final int possibleW) {
    final boolean zIndexWithinArray = possibleZ > 0 && possibleZ <= array.length;
    final boolean yIndexWithinArray = possibleY > 0 && possibleY <= array[possibleZ - 1].length;
    final boolean xIndexWithinArray = possibleX > 0 && possibleX <= array[possibleZ - 1][possibleY - 1].length;
    final boolean wIndexWithinArray =
        possibleW > 0 && possibleW <= array[possibleZ - 1][possibleY - 1][possibleX - 1].length;

    return zIndexWithinArray && yIndexWithinArray && xIndexWithinArray && wIndexWithinArray;
  }
}
