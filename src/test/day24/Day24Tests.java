package test.day24;

import me.newceptiondev.day24.Day24;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day24Tests {

  @Test
  public void task1ExampleTest() {
    List<String> inputs =
        List.of("sesenwnenenewseeswwswswwnenewsewsw", "neeenesenwnwwswnenewnwwsewnenwseswesw", "seswneswswsenwwnwse",
                "nwnwneseeswswnenewneswwnewseswneseene", "swweswneswnenwsewnwneneseenw",
                "eesenwseswswnenwswnwnwsewwnwsene", "sewnenenenesenwsewnenwwwse", "wenwwweseeeweswwwnwwe",
                "wsweesenenewnwwnwsenewsenwwsesesenwne", "neeswseenwwswnwswswnw",
                "nenwswwsewswnenenewsenwsenwnesesenew", "enewnwewneswsewnwswenweswnenwsenwsw",
                "sweneswneswneneenwnewenewwneswswnese", "swwesenesewenwneswnwwneseswwne",
                "enesenwswwswneneswsenwnewswseenwsese", "wnwnesenesenenwwnenwsewesewsesesew",
                "nenewswnwewswnenesenwnesewesw", "eneswnwswnwsenenwnwnwwseeswneewsenese",
                "neswnwewnwnwseenwseesewsenwsweewe", "wseweeenwnesenwwwswnew");

    Day24 day = new Day24();

    assertEquals(10, day.task1(inputs));
  }

  @Test
  public void task2ExampleTest() {
    List<String> inputs =
        List.of("sesenwnenenewseeswwswswwnenewsewsw", "neeenesenwnwwswnenewnwwsewnenwseswesw", "seswneswswsenwwnwse",
                "nwnwneseeswswnenewneswwnewseswneseene", "swweswneswnenwsewnwneneseenw",
                "eesenwseswswnenwswnwnwsewwnwsene", "sewnenenenesenwsewnenwwwse", "wenwwweseeeweswwwnwwe",
                "wsweesenenewnwwnwsenewsenwwsesesenwne", "neeswseenwwswnwswswnw",
                "nenwswwsewswnenenewsenwsenwnesesenew", "enewnwewneswsewnwswenweswnenwsenwsw",
                "sweneswneswneneenwnewenewwneswswnese", "swwesenesewenwneswnwwneseswwne",
                "enesenwswwswneneswsenwnewswseenwsese", "wnwnesenesenenwwnenwsewesewsesesew",
                "nenewswnwewswnenesenwnesewesw", "eneswnwswnwsenenwnwnwwseeswneewsenese",
                "neswnwewnwnwseenwseesewsenwsweewe", "wseweeenwnesenwwwswnew");

    Day24 day = new Day24();

    final int expected = 2208;
    assertEquals(expected, day.task2(inputs));
  }
}
