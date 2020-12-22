package test.day22;

import me.newceptiondev.day22.Day22;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day22Tests {

    @Test public void task2ExampleTest() {
        List<String> input =
                List.of("Player 1:", "9", "2", "6", "3", "1", "", "Player 2:", "5", "8", "4", "7",
                        "10");

        Day22 day = new Day22();

        assertEquals(291, day.task2(input));
    }
}
