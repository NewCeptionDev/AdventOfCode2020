package test.day12;

import me.newceptiondev.day12.Day12;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day12Tests {

    @Test
    public void task2ExampleTest() {

        List<String> input = List.of("F10", "N3", "F7", "R90", "F11");

        Day12 day = new Day12();

        assertEquals(286, day.task2(input));
    }

    @Test
    public void task2CustomTest1() {
        List<String> input = List.of("F10", "N3", "F7", "L90", "F11");

        Day12 day = new Day12();

        assertEquals(274, day.task2(input));
    }

    @Test
    public void task2CustomTest2() {
        List<String> input = List.of("F10", "N3", "F7", "L90", "R90", "R90", "F11");

        Day12 day = new Day12();

        assertEquals(286, day.task2(input));
    }

    @Test
    public void task2CustomTest3() {
        List<String> input = List.of("F10", "N3", "F7", "R90", "S4", "F11");

        Day12 day = new Day12();

        assertEquals(330, day.task2(input));
    }

    @Test
    public void task2CustomTest4() {
        List<String> input = List.of("F10", "N3", "F7", "R90", "N4", "F11");

        Day12 day = new Day12();

        assertEquals(242, day.task2(input));
    }

    @Test
    public void task2CustomTest5() {
        List<String> input = List.of("F10", "N3", "F7", "R90", "E4", "F11");

        Day12 day = new Day12();

        assertEquals(330, day.task2(input));
    }

    @Test
    public void task2CustomTest6() {
        List<String> input = List.of("F10", "N3", "F7", "R90", "W4", "F11");

        Day12 day = new Day12();

        assertEquals(242, day.task2(input));
    }

    @Test
    public void task2CustomTest7() {
        List<String> input = List.of("F10", "N3", "F7", "R90", "R90", "F11");

        Day12 day = new Day12();

        assertEquals(66, day.task2(input));
    }

    @Test
    public void task2CustomTest8() {
        List<String> input = List.of("F10", "N3", "F7", "R180", "F11");

        Day12 day = new Day12();

        assertEquals(66, day.task2(input));
    }
}
