package me.newceptiondev.day24;

import me.newceptiondev.util.FileUtil;
import me.newceptiondev.util.Tuple;

import java.util.*;

public class Day24 {

  private static final String FILE_NAME = "day24Task1Input";
  private static final double DIAGONAL_STEP = 0.5;

  public static void main(String[] args) {
    List<String> inputs = FileUtil.readFileAsListOfLines(FILE_NAME);

    new Day24(inputs);
  }

  public Day24() {
  }

  private Day24(List<String> inputs) {
    System.out.println("Task 1: " + task1(inputs));
    System.out.println("Task 2: " + task2(inputs));
  }

  /**
   * Task 1
   *
   * @param inputs List of String
   *
   * @return Count of Tiles that have the black side up
   */
  public int task1(final List<String> inputs) {
    Map<Double, Set<Double>> flippedTiles = parseBlackTilePositions(inputs);

    return flippedTiles.values().stream().mapToInt(Set::size).sum();
  }

  /**
   * Task 2
   *
   * @param inputs List of String
   *
   * @return Count of Tiles that have the black side up
   */
  public int task2(final List<String> inputs) {
    Map<Double, Set<Double>> flippedTiles = parseBlackTilePositions(inputs);

    for(int i = 0; i < 100; i++) {
      flippedTiles = flippedTilesAfterDay(flippedTiles);
    }

    return flippedTiles.values().stream().mapToInt(Set::size).sum();
  }

  /**
   * Parses all Tiles to their Positions and flips them
   *
   * @param inputs List of String
   *
   * @return Map with Positions of Tiles that have their Black Side facing up
   */
  private Map<Double, Set<Double>> parseBlackTilePositions(final List<String> inputs) {
    Map<Double, Set<Double>> tilePositions = new HashMap<>();

    for(String tileInputs : inputs) {
      Tuple<Double, Double> tilePosition = determineTilePosition(tileInputs);

      if(tilePositions.containsKey(tilePosition.getY())) {
        if(tilePositions.get(tilePosition.getY()).contains(tilePosition.getX())) {
          tilePositions.get(tilePosition.getY()).remove(tilePosition.getX());
        } else {
          tilePositions.get(tilePosition.getY()).add(tilePosition.getX());
        }
      } else {
        Set<Double> tileList = new HashSet<>();
        tileList.add(tilePosition.getX());

        tilePositions.put(tilePosition.getY(), tileList);
      }
    }

    return tilePositions;
  }

  /**
   * Determines the Position of a Tile
   *
   * @param tileInputs Instructions how to get to the Tile Position
   *
   * @return Tuple with Tile Position
   */
  private Tuple<Double, Double> determineTilePosition(final String tileInputs) {
    char[] inputChars = tileInputs.toCharArray();

    double currentX = 0;
    double currentY = 0;

    boolean halfStep = false;

    for(final char inputChar : inputChars) {
      switch(inputChar) {
        case 'e':
          if(halfStep) {
            currentX += DIAGONAL_STEP;
            halfStep = false;
          } else {
            currentX += 1;
          }
          break;
        case 'w':
          if(halfStep) {
            currentX -= DIAGONAL_STEP;
            halfStep = false;
          } else {
            currentX -= 1;
          }
          break;
        case 's':
          currentY -= 1;
          halfStep = true;
          break;
        case 'n':
          currentY += 1;
          halfStep = true;
      }
    }

    return new Tuple<>(currentX, currentY);
  }

  /**
   * Flips the Tiles according to the Rules
   *
   * @param currentlyFlippedTiles Flipped Tiles for current Day
   *
   * @return Tiles that are flipped on the next Day
   */
  private Map<Double, Set<Double>> flippedTilesAfterDay(final Map<Double, Set<Double>> currentlyFlippedTiles) {
    Map<Double, Set<Double>> nextDay = new HashMap<>();

    Set<Tuple<Double, Double>> adjacentWhiteTiles = new HashSet<>();

    for(Double yValue : currentlyFlippedTiles.keySet()) {
      for(Double xValue : currentlyFlippedTiles.get(yValue)) {
        adjacentWhiteTiles.addAll(findAllWhiteNeighbourTiles(currentlyFlippedTiles, new Tuple<>(xValue, yValue)));

        int countOfAdjacentBlackTiles =
            countOfDirectlyAdjacentBlackTiles(currentlyFlippedTiles, new Tuple<>(xValue, yValue));

        if(countOfAdjacentBlackTiles == 1 || countOfAdjacentBlackTiles == 2) {
          nextDay.putIfAbsent(yValue, new HashSet<>());
          nextDay.get(yValue).add(xValue);
        }
      }
    }

    for(Tuple<Double, Double> whiteTile : adjacentWhiteTiles) {
      if(countOfDirectlyAdjacentBlackTiles(currentlyFlippedTiles, whiteTile) == 2) {
        nextDay.putIfAbsent(whiteTile.getY(), new HashSet<>());
        nextDay.get(whiteTile.getY()).add(whiteTile.getX());
      }
    }

    return nextDay;
  }

  /**
   * Calculates all Neighbour Positions for a given Position
   *
   * @param xValue xValue of Position
   * @param yValue yValue of Position
   *
   * @return Set with Tuples of Positions
   */
  private Set<Tuple<Double, Double>> calculateNeighbourPositions(final Double xValue, final Double yValue) {
    Set<Tuple<Double, Double>> neighbours = new HashSet<>();

    neighbours.add(new Tuple<>(xValue - DIAGONAL_STEP, yValue + 1));
    neighbours.add(new Tuple<>(xValue - DIAGONAL_STEP, yValue - 1));
    neighbours.add(new Tuple<>(xValue + DIAGONAL_STEP, yValue + 1));
    neighbours.add(new Tuple<>(xValue + DIAGONAL_STEP, yValue - 1));
    neighbours.add(new Tuple<>(xValue + 1, yValue));
    neighbours.add(new Tuple<>(xValue - 1, yValue));

    return neighbours;
  }

  /**
   * Counts the directly Adjacent Tiles that have their black Side facing up
   *
   * @param flippedTiles    Tiles with black Side facing up
   * @param currentPosition Current Position
   *
   * @return Count of Tiles that have their black Side facing up and are Neighbour Tiles of the given Position
   */
  private int countOfDirectlyAdjacentBlackTiles(final Map<Double, Set<Double>> flippedTiles,
                                                final Tuple<Double, Double> currentPosition) {
    Set<Tuple<Double, Double>> neighbourPositions =
        calculateNeighbourPositions(currentPosition.getX(), currentPosition.getY());

    int adjacentBlackTiles = 0;

    for(Tuple<Double, Double> adjacentPosition : neighbourPositions) {
      if(flippedTiles.containsKey(adjacentPosition.getY()) &&
         flippedTiles.get(adjacentPosition.getY()).contains(adjacentPosition.getX())) {
        adjacentBlackTiles++;
      }
    }

    return adjacentBlackTiles;
  }

  /**
   * Finds all Neighbour Tiles for a given Position that have their white Side facing up
   *
   * @param tileMap  Tiles with their black Side facing up
   * @param position Given Position
   *
   * @return Set of Positions that are Neighbours of the given Position and have their white Side facing up
   */
  private Set<Tuple<Double, Double>> findAllWhiteNeighbourTiles(Map<Double, Set<Double>> tileMap,
                                                                Tuple<Double, Double> position) {
    Set<Tuple<Double, Double>> neighbourTiles = calculateNeighbourPositions(position.getX(), position.getY());
    Set<Tuple<Double, Double>> whiteNeighbourTiles = new HashSet<>();

    for(Tuple<Double, Double> tile : neighbourTiles) {
      if(!tileMap.containsKey(tile.getY()) || !tileMap.get(tile.getY()).contains(tile.getX())) {
        whiteNeighbourTiles.add(tile);
      }

    }

    return whiteNeighbourTiles;
  }
}
