package test.day25;

import me.newceptiondev.day25.Day25;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day25Tests {

  @Test
  public void task1ExampleTest() {
    List<String> input = List.of("5764801", "17807724");

    Day25 day = new Day25();

    final int expected = 14897079;
    assertEquals(expected, day.task1(input));
  }
}
