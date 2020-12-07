package test.day7;

import me.newceptiondev.day7.Day7;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day7Tests {

    @Test
    public void task2Example() {
        List<String> input = List.of("shiny gold bags contain 2 dark red bags.",
                "dark red bags contain 2 dark orange bags.",
                "dark orange bags contain 2 dark yellow bags.",
                "dark yellow bags contain 2 dark green bags.",
                "dark green bags contain 2 dark blue bags.",
                "dark blue bags contain 2 dark violet bags.",
                "dark violet bags contain no other bags.");

        Day7 day = new Day7();

        int result = day.task2(input);

        assertEquals(126, result);
    }

}
