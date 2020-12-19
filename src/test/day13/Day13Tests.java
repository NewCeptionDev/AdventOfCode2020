package test.day13;

import me.newceptiondev.day13.Day13;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day13Tests {

    @Test
    public void Task2Example1Test(){

        List<String> inputs = List.of("", "17,x,13,19");

        Day13 day = new Day13();

        assertEquals(3417, day.task2(inputs));
    }

    @Test
    public void Task2Example2Test(){

        List<String> inputs = List.of("", "67,7,59,61");

        Day13 day = new Day13();

        assertEquals(754018, day.task2(inputs));
    }

    @Test
    public void Task2Example3Test(){

        List<String> inputs = List.of("", "67,x,7,59,61");

        Day13 day = new Day13();

        assertEquals(779210, day.task2(inputs));
    }

    @Test
    public void Task2Example4Test(){

        List<String> inputs = List.of("", "67,7,x,59,61");

        Day13 day = new Day13();

        assertEquals(1261476, day.task2(inputs));
    }

    @Test
    public void Task2Example5Test(){

        List<String> inputs = List.of("", "1789,37,47,1889");

        Day13 day = new Day13();

        assertEquals(1202161486, day.task2(inputs));
    }
}
