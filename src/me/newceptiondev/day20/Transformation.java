package me.newceptiondev.day20;

import java.util.List;

public class Transformation {

    enum ACTION {
        NONE,
        FLIP,
        ROTATE
    }

    ACTION action;
    List<Integer> rotationMap;

    public Transformation(final ACTION action, final List<Integer> rotationMap) {
        this.action = action;
        this.rotationMap = rotationMap;
    }
}
