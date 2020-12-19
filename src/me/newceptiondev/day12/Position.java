package me.newceptiondev.day12;

import me.newceptiondev.util.Tuple;

public class Position {

    private final int east;
    private final int north;
    private Direction direction;
    private Tuple<Integer, Integer> waypoint;

    public Position(int east, int north, Direction direction){
        this.east = east;
        this.north = north;
        this.direction = direction;
    }

    public Position(int east, int north, Tuple<Integer, Integer> waypoint){
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

    public int calculateManhattanPositionToZero(){
        return Math.abs(this.east) + Math.abs(this.north);
    }

    @Override
    public String toString() {
        return "Position{" +
                "east=" + east +
                ", north=" + north +
                ", direction=" + direction +
                ", waypoint=" + waypoint +
                '}';
    }
}
