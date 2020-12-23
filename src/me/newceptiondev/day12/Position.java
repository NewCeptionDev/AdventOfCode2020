package me.newceptiondev.day12;

import me.newceptiondev.util.Tuple;

public class Position {

  private final int east;
  private final int north;
  private Direction direction;
  private Tuple<Integer, Integer> waypoint;

  /**
   * Creates a Position with east, north and a Direction
   *
   * @param east      East Value
   * @param north     North Value
   * @param direction Direction
   */
  public Position(final int east, final int north, final Direction direction) {
    this.east = east;
    this.north = north;
    this.direction = direction;
  }

  /**
   * Creates a Position with east, north and a Waypoint
   *
   * @param east     East Value
   * @param north    North Value
   * @param waypoint Waypoint
   */
  public Position(final int east, final int north, final Tuple<Integer, Integer> waypoint) {
    this.east = east;
    this.north = north;
    this.waypoint = waypoint;
  }

  public int getEast() {
    return east;
  }

  public int getNorth() {
    return north;
  }

  public Direction getDirection() {
    return direction;
  }

  public Tuple<Integer, Integer> getWaypoint() {
    return waypoint;
  }

  /**
   * Calculates the Manhattan Distance between east, north and 0,0
   *
   * @return Manhattan Distance
   */
  public int calculateManhattanPositionToZero() {
    return Math.abs(east) + Math.abs(north);
  }
}
