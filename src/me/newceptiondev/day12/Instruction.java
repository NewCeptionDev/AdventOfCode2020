package me.newceptiondev.day12;

import me.newceptiondev.util.Tuple;

public class Instruction {

  private static final int DEGREES_90 = 90;
  private static final int DEGREES_180 = 180;
  private static final int DEGREES_270 = 270;
  private final Action action;
  private final int value;

  /**
   * Creates an Instruction from a String
   *
   * @param code String describing Instruction
   */
  public Instruction(final String code) {
    action = parseAction(code.charAt(0));
    value = Integer.parseInt(code.substring(1));
  }

  /**
   * Parses the Action from a char
   *
   * @param actionChar Char describing Action
   *
   * @return Action described by Char
   */
  private Action parseAction(final char actionChar) {
    switch(actionChar) {
      case 'N':
        return Action.NORTH;
      case 'E':
        return Action.EAST;
      case 'S':
        return Action.SOUTH;
      case 'W':
        return Action.WEST;
      case 'L':
        return Action.LEFT;
      case 'R':
        return Action.RIGHT;
      case 'F':
        return Action.FORWARD;
    }

    System.err.println("There was an Action that could not be parsed");
    return null;
  }

  /**
   * Applies the Instruction to the given Position according to the Instruction Set of Task 1
   *
   * @param currentPosition Position Current Position
   *
   * @return Position after Instruction
   */
  public Position applyInstructionForTask1(final Position currentPosition) {

    switch(action) {
      case LEFT:
      case RIGHT:
        return new Position(currentPosition.getEast(), currentPosition.getNorth(),
                            turnShip(currentPosition.getDirection()));
      case FORWARD:
        return moveTowardsDirection(currentPosition, currentPosition.getDirection());
      case NORTH:
        return moveTowardsDirection(currentPosition, Direction.NORTH);
      case EAST:
        return moveTowardsDirection(currentPosition, Direction.EAST);
      case WEST:
        return moveTowardsDirection(currentPosition, Direction.WEST);
      case SOUTH:
        return moveTowardsDirection(currentPosition, Direction.SOUTH);
    }

    System.err.println("There was an Error parsing the Action");
    return null;
  }

  /**
   * Applies the Instruction to the given Position according to the Instruction Set of Task 2
   *
   * @param currentPosition Position Current Position
   *
   * @return Position after Instruction
   */
  public Position applyInstructionForTask2(final Position currentPosition) {

    switch(action) {
      case LEFT:
      case RIGHT:
        return new Position(currentPosition.getEast(), currentPosition.getNorth(),
                            rotateWaypoint(currentPosition.getWaypoint()));
      case FORWARD:
        return moveTowardsWaypoint(currentPosition);
      case NORTH:
        return new Position(currentPosition.getEast(), currentPosition.getNorth(),
                            moveWaypoint(currentPosition.getWaypoint(), Direction.NORTH));
      case EAST:
        return new Position(currentPosition.getEast(), currentPosition.getNorth(),
                            moveWaypoint(currentPosition.getWaypoint(), Direction.EAST));
      case WEST:
        return new Position(currentPosition.getEast(), currentPosition.getNorth(),
                            moveWaypoint(currentPosition.getWaypoint(), Direction.WEST));
      case SOUTH:
        return new Position(currentPosition.getEast(), currentPosition.getNorth(),
                            moveWaypoint(currentPosition.getWaypoint(), Direction.SOUTH));
    }

    System.err.println("There was an Error parsing the Action");
    return null;
  }

  /**
   * Turns the Ship by the given Direction
   *
   * @param currentDirection Direction
   *
   * @return New Direction of Shop
   */
  private Direction turnShip(final Direction currentDirection) {

    int directionChange = 0;

    if(value == DEGREES_90) {
      directionChange = 1;
    } else if(value == DEGREES_180) {
      directionChange = 2;
    } else if(value == DEGREES_270) {
      directionChange = 3;
    }

    int newDirection;

    if(action == Action.LEFT) {
      newDirection = ((currentDirection.ordinal() - directionChange) + 4) % 4;

    } else {
      newDirection = (currentDirection.ordinal() + directionChange) % 4;
    }

    return Direction.values()[newDirection];
  }

  /**
   * Moves the Ship towards the given Direction
   *
   * @param currentPosition Position Current Position
   * @param direction       Direction to move towards
   *
   * @return New Position of Ship
   */
  private Position moveTowardsDirection(final Position currentPosition, final Direction direction) {

    switch(direction) {
      case NORTH:
        return new Position(currentPosition.getEast(), currentPosition.getNorth() + value,
                            currentPosition.getDirection());
      case EAST:
        return new Position(currentPosition.getEast() + value, currentPosition.getNorth(),
                            currentPosition.getDirection());
      case WEST:
        return new Position(currentPosition.getEast() - value, currentPosition.getNorth(),
                            currentPosition.getDirection());
      case SOUTH:
        return new Position(currentPosition.getEast(), currentPosition.getNorth() - value,
                            currentPosition.getDirection());
    }

    System.err.println("Direction could not be parsed");
    return null;
  }

  /**
   * Rotates the given Waypoint
   *
   * @param waypoint Given Waypoint
   *
   * @return New Waypoint
   */
  private Tuple<Integer, Integer> rotateWaypoint(final Tuple<Integer, Integer> waypoint) {

    if(action == Action.LEFT) {
      switch(value) {
        case DEGREES_90:
          return new Tuple<>(-1 * waypoint.getY(), waypoint.getX());
        case DEGREES_180:
          return new Tuple<>(-1 * waypoint.getX(), -1 * waypoint.getY());
        case DEGREES_270:
          return new Tuple<>(waypoint.getY(), -1 * waypoint.getX());
        default:
          return waypoint;
      }
    } else {
      switch(value) {
        case DEGREES_90:
          return new Tuple<>(waypoint.getY(), -1 * waypoint.getX());
        case DEGREES_180:
          return new Tuple<>(-1 * waypoint.getX(), -1 * waypoint.getY());
        case DEGREES_270:
          return new Tuple<>(-1 * waypoint.getY(), waypoint.getX());
        default:
          return waypoint;
      }
    }
  }

  /**
   * Moves the Ship towards the Waypoint
   *
   * @param currentPosition Position Current Position
   *
   * @return New Position after moving
   */
  private Position moveTowardsWaypoint(final Position currentPosition) {
    return new Position(currentPosition.getEast() + (value * currentPosition.getWaypoint().getX()),
                        currentPosition.getNorth() + (value * currentPosition.getWaypoint().getY()),
                        currentPosition.getWaypoint());
  }

  /**
   * Moves the given Waypoint in the given Direction
   *
   * @param waypoint  Waypoint Current Waypoint
   * @param direction Direction to move towards
   *
   * @return New Waypoint
   */
  private Tuple<Integer, Integer> moveWaypoint(final Tuple<Integer, Integer> waypoint, final Direction direction) {
    switch(direction) {
      case NORTH:
        return new Tuple<>(waypoint.getX(), waypoint.getY() + value);
      case EAST:
        return new Tuple<>(waypoint.getX() + value, waypoint.getY());
      case WEST:
        return new Tuple<>(waypoint.getX() - value, waypoint.getY());
      case SOUTH:
        return new Tuple<>(waypoint.getX(), waypoint.getY() - value);
    }

    System.err.println("Direction could not be parsed");
    return null;
  }
}
