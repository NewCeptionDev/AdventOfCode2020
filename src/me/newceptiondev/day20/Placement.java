package me.newceptiondev.day20;

import java.util.List;
import java.util.Set;

public class Placement {

    private int size;
    private int xPos;
    private int yPos;
    private List<Integer> rotationMap;

    public Placement(final int size, final int xPos, final int yPos, final List<Integer> rotationMap) {
        this.size = size;
        this.xPos = xPos;
        this.yPos = yPos;
        this.rotationMap = rotationMap;
    }

    public String asString() {
        return xPos + ":" + yPos;
    }

    public Placement connect(int side, List<Integer> rotationMap){
        int newY = yPos;
        int newX = xPos;

        if(side == 0) {
            // Connect at Top
            newY -= 1;
        } else if(side == 1) {
            // Connect at Left
            newX -= 1;
        } else if(side == 2) {
            // Connect at Right
            newX += 1;
        } else if(side == 3) {
            // Connect at Bottom
            yPos += 1;
        }
        return new Placement(size, newX, newY, rotationMap);
    }

    // connections of first tile decides its placement
    // 0 TOP, 1 LEFT, 2 RIGHT, 3 BOTTOM
    // connected at 1 and 3 (sum 5) === top right corner
    // connected at 2 and 3 (sum 4) === top left corner
    // connected at 0 and 2 (sum 2) === bottom left corner
    // connected at 0 and 1 (sum 1) === bottom right corner
    public static Placement first(Tile tile, int size) {
        Set<Integer> connectionKeys = tile.getConnections().keySet();
        int sum = connectionKeys.stream().reduce(0, Integer::sum);
        int newY = 0;
        int newX = 0;

        if(sum == 4) {
            newX = size - 1;
        } else if(sum == 2) {
            newY = size - 1;
        } else if(sum == 1){
            newX = size - 1;
            newY = size - 1;
        }

        return new Placement(size, newX, newY, List.of(0,1,2,3));
    }

    public List<Integer> getRotationMap() {
        return rotationMap;
    }
}
