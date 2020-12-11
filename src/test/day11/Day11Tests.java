package test.day11;

import me.newceptiondev.day11.Day11;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day11Tests {

    @Test
    public void Task2ExampleTest() {

        List<String> input = List.of(
                "L.LL.LL.LL",
                "LLLLLLL.LL",
                "L.L.L..L..",
                "LLLL.LL.LL",
                "L.LL.LL.LL",
                "L.LLLLL.LL",
                "..L.L.....",
                "LLLLLLLLLL",
                "L.LLLLLL.L",
                "L.LLLLL.LL"
        );

        Day11 day = new Day11();

        assertEquals(26, day.task2(input));
    }

}
