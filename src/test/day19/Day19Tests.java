package test.day19;

import me.newceptiondev.day19.Day19;
import org.junit.jupiter.api.Test;

import java.util.List;

public class Day19Tests {

    @Test
    public void task1Example1Test() {
        List<String> input = List.of("0: 1 2",
                "1: \"a\"",
                "2: 1 3 | 3 1",
                "3: \"b\"");

        Day19 day = new Day19();

        day.task1(input);
    }


    @Test
    public void task1Example2Test() {
        List<String> input = List.of("0: 4 1 5",
        "1: 2 3 | 3 2",
        "2: 4 4 | 5 5",
        "3: 4 5 | 5 4",
        "4: \"a\"",
        "5: \"b\"");

        Day19 day = new Day19();

        day.task1(input);
    }

}
