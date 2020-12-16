package test.day14;

import me.newceptiondev.day14.Day14;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day14Tests {

    @Test
    public void Task2ExampleTest() {
        List<String> input = List.of("mask = 000000000000000000000000000000X1001X",
                "mem[42] = 100",
                "mask = 00000000000000000000000000000000X0XX",
                "mem[26] = 1");

        Day14 day = new Day14();

        assertEquals(208, day.task2(input));
    }

}
