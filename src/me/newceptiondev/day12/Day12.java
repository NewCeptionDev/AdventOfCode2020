package me.newceptiondev.day12;

import me.newceptiondev.util.FileUtil;
import me.newceptiondev.util.Tuple;

import java.util.List;
import java.util.stream.Collectors;

public class Day12 {

  public static final String FILE_NAME = "day12Task1Input";

  public static void main(String[] args) {
    List<String> inputs = FileUtil.readFileAsListOfLines(FILE_NAME);

    new Day12(inputs);
  }

  public Day12() {
  }

  private Day12(List<String> inputs) {
    System.out.println("Task 1: " + task1(inputs));
    System.out.println("Task 2: " + task2(inputs));
  }

  /**
   * Task 1
   *
   * @param inputs List of String
   *
   * @return Manhattan distance between starting position and current Position with Instruction Set of Task 1
   */
  public int task1(final List<String> inputs) {
    List<Instruction> instructions = inputs.stream().map(Instruction::new).collect(Collectors.toList());

    Position currentPosition = new Position(0, 0, Direction.EAST);

    for(Instruction instruction : instructions) {
      currentPosition = instruction.applyInstructionForTask1(currentPosition);
    }

    return currentPosition.calculateManhattanPositionToZero();
  }

  /**
   * Task 2
   *
   * @param inputs List of String
   *
   * @return Manhattan distance between starting position and current Position with Instruction Set of Task 2
   */
  public int task2(final List<String> inputs) {
    List<Instruction> instructions = inputs.stream().map(Instruction::new).collect(Collectors.toList());

    Position currentPosition = new Position(0, 0, new Tuple<>(10, 1));

    for(Instruction instruction : instructions) {
      currentPosition = instruction.applyInstructionForTask2(currentPosition);
    }

    return currentPosition.calculateManhattanPositionToZero();
  }

}
