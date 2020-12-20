package test.day10;

import me.newceptiondev.day10.Day10;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day10Tests {

    @Test
    public void task2ExampleTest() {
        List<String> inputs = List.of("16", "10", "15", "5", "1", "11", "7", "19", "6", "12", "4");

        Day10 day = new Day10();

        assertEquals(8, day.task2(inputs));
    }

}
