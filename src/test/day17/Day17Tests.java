package test.day17;

import me.newceptiondev.day17.Day17;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day17Tests {

    @Test
    public void task1ExampleTest() {
        List<String> input = List.of(".#.",
                "..#",
                "###");

        Day17 day = new Day17();

        assertEquals(112, day.task1(input));
    }
}
