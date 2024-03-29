package test.day18;

import me.newceptiondev.day18.Day18;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day18Tests {

    @Test
    public void task1Example1Test(){

        List<String> inputs = List.of("2 * 3 + (4 * 5)");

        Day18 day = new Day18();

        assertEquals(26, day.task1(inputs));
    }

    @Test
    public void task2Example1Test(){

        List<String> inputs = List.of("1 + 2 * 3 + 4 * 5 + 6");

        Day18 day = new Day18();

        assertEquals(BigInteger.valueOf(231), day.task2(inputs));
    }

    @Test
    public void task2Example2Test(){

        List<String> inputs = List.of("1 + (2 * 3) + (4 * (5 + 6))");

        Day18 day = new Day18();

        assertEquals(BigInteger.valueOf(51), day.task2(inputs));
    }
}
