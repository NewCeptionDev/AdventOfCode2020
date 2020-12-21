package test.day21;

import me.newceptiondev.day21.Day21;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day21Tests {

    @Test public void task1ExampleTest() {
        List<String> input = List.of("mxmxvkd kfcds sqjhc nhms (contains dairy, fish)",
                "trh fvjkl sbzzf mxmxvkd (contains dairy)", "sqjhc fvjkl (contains soy)",
                "sqjhc mxmxvkd sbzzf (contains fish)");

        Day21 day = new Day21();

        assertEquals(5, day.task1(input));
    }

    @Test public void task2ExampleTest() {
        List<String> input = List.of("mxmxvkd kfcds sqjhc nhms (contains dairy, fish)",
                "trh fvjkl sbzzf mxmxvkd (contains dairy)", "sqjhc fvjkl (contains soy)",
                "sqjhc mxmxvkd sbzzf (contains fish)");

        Day21 day = new Day21();

        assertEquals("mxmxvkd,sqjhc,fvjkl", day.task2(input));
    }
}
