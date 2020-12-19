package test.day12;

import me.newceptiondev.day12.Day12;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day12Tests {

    @Test
    public void Task2ExampleTest() {

        List<String> input = List.of(
                "F10",
                "N3",
                "F7",
                "R90",
                "F11"
        );

        Day12 day = new Day12();

        assertEquals(286, day.task2(input));
    }
}
