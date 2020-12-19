package me.newceptiondev.day12;

import me.newceptiondev.util.Tuple;

public class Instruction {

    private final Action action;
    private final int value;

    public Instruction(String code) {
        this.action = parseAction(code.charAt(0));
        this.value = Integer.parseInt(code.substring(1));
    }

    private Action parseAction(char actionChar) {
        switch (actionChar) {
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

    public Position applyInstructionForTask1(Position currentPosition) {

        switch (this.action) {
            case LEFT:
            case RIGHT:
                return new Position(currentPosition.getEast(), currentPosition.getNorth(),
                        turnShip(currentPosition.getDirection()));
            case FORWARD:
                return driveTowardsDirection(currentPosition, currentPosition.getDirection());
            case NORTH:
                return driveTowardsDirection(currentPosition, Direction.NORTH);
            case EAST:
                return driveTowardsDirection(currentPosition, Direction.EAST);
            case WEST:
                return driveTowardsDirection(currentPosition, Direction.WEST);
            case SOUTH:
                return driveTowardsDirection(currentPosition, Direction.SOUTH);
        }

        System.err.println("There was an Error parsing the Action");
        return null;
    }

    public Position applyInstructionForTask2(Position currentPosition) {

        switch (this.action) {
            case LEFT:
            case RIGHT:
                return new Position(currentPosition.getEast(), currentPosition.getNorth(),
                        rotateWaypoint(currentPosition.getWaypoint()));
            case FORWARD:
                return driveTowardsWaypoint(currentPosition);
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

    private Direction turnShip(Direction currentDirection) {

        int directionChange = 0;

        if (this.value == 90) {
            directionChange = 1;
        } else if (this.value == 180) {
            directionChange = 2;
        } else if (this.value == 270) {
            directionChange = 3;
        }

        int newDirection;

        if (this.action == Action.LEFT) {
            newDirection = ((currentDirection.ordinal() - directionChange) + 4) % 4;

        } else {
            newDirection = (currentDirection.ordinal() + directionChange) % 4;
        }

        return Direction.values()[newDirection];
    }

    private Position driveTowardsDirection(Position currentPosition, Direction direction) {

        switch (direction) {
            case NORTH:
                return new Position(currentPosition.getEast(),
                        currentPosition.getNorth() + this.value, currentPosition.getDirection());
            case EAST:
                return new Position(currentPosition.getEast() + this.value,
                        currentPosition.getNorth(), currentPosition.getDirection());
            case WEST:
                return new Position(currentPosition.getEast() - this.value,
                        currentPosition.getNorth(), currentPosition.getDirection());
            case SOUTH:
                return new Position(currentPosition.getEast(),
                        currentPosition.getNorth() - this.value, currentPosition.getDirection());
        }

        System.err.println("Direction could not be parsed");
        return null;
    }

    private Tuple<Integer, Integer> rotateWaypoint(Tuple<Integer, Integer> wayPointDistance) {

        if (this.action == Action.LEFT) {
            if (this.value == 90) {
                return new Tuple<>(-1 * wayPointDistance.getY(), wayPointDistance.getX());
            } else if (this.value == 180) {
                return new Tuple<>(wayPointDistance.getX(), -1 * wayPointDistance.getY());
            } else if (this.value == 270) {
                return new Tuple<>(wayPointDistance.getY(), -1 * wayPointDistance.getX());
            } else {
                return wayPointDistance;
            }
        } else {
            if (this.value == 90) {
                return new Tuple<>(wayPointDistance.getY(), -1 * wayPointDistance.getX());
            } else if (this.value == 180) {
                return new Tuple<>(wayPointDistance.getX(), -1 * wayPointDistance.getY());
            } else if (this.value == 270) {
                return new Tuple<>(-1 * wayPointDistance.getY(), wayPointDistance.getX());
            } else {
                return wayPointDistance;
            }
        }
    }

    private Position driveTowardsWaypoint(Position currentPosition) {
        return new Position(
                currentPosition.getEast() + (this.value * currentPosition.getWaypoint().getX()),
                currentPosition.getNorth() + (this.value * currentPosition.getWaypoint().getY()),
                currentPosition.getWaypoint());
    }

    private Tuple<Integer, Integer> moveWaypoint(Tuple<Integer, Integer> waypoint, Direction direction) {
        switch (direction) {
            case NORTH:
                return new Tuple<>(waypoint.getX(), waypoint.getY() + this.value);
            case EAST:
                return new Tuple<>(waypoint.getX() + this.value, waypoint.getY());
            case WEST:
                return new Tuple<>(waypoint.getX() - this.value, waypoint.getY());
            case SOUTH:
                return new Tuple<>(waypoint.getX(), waypoint.getY() - this.value);
        }

        System.err.println("Direction could not be parsed");
        return null;
    }
}
