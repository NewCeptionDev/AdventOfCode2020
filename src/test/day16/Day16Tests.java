package test.day16;

import me.newceptiondev.day16.Day16;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day16Tests {

    @Test
    public void Task2ExampleTest() {
        List<String> input = List.of("class: 0-1 or 4-19" , "row: 0-5 or 8-19" , "seat: 0-13 or 16-19" , "" ,
                                     "your ticket:" , "11,12,13" , "" , "nearby tickets:" , "3,9,18" ,
                                     "15,1,5" , "5,14,9");

        Day16 day = new Day16();

        assertEquals(1, day.task2(input));
    }

}
