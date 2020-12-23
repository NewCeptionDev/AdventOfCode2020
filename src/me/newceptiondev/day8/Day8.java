package me.newceptiondev.day8;

import me.newceptiondev.util.FileUtil;
import me.newceptiondev.util.Tuple;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class Day8 {

  private static final String FILE_NAME = "day8Task1Input";

  public static void main(String[] args) {
    List<String> inputs = FileUtil.readFileAsListOfLines(FILE_NAME);

    new Day8(inputs);
  }

  private Day8(List<String> inputs) {
    System.out.println("Task 1: " + task1(inputs));
    System.out.println("Task 2: " + task2(inputs));
  }

  /**
   * Task 1
   *
   * @param inputs List of String
   *
   * @return Accumulator immediately before first Operation is executed for a second Time
   */
  public int task1(List<String> inputs) {
    List<Instruction> instructions = inputs.stream().map(Instruction::new).collect(Collectors.toList());

    return runProgramm(instructions).getX();
  }

  /**
   * Task 2
   *
   * @param inputs List of String
   *
   * @return Accumulator after the Code was fully executed
   */
  public int task2(List<String> inputs) {
    List<Instruction> instructions = inputs.stream().map(Instruction::new).collect(Collectors.toList());

    for(int i = 0; i < instructions.size(); i++) {
      Instruction instruction = instructions.get(i);

      Tuple<Integer, Boolean> testResult = null;

      if(instruction.getOperation() == Operation.JUMP) {
        testResult = switchInstructionAndRun(instructions, i, Operation.NO_OPERATION);
      } else if(instruction.getOperation() == Operation.NO_OPERATION) {
        testResult = switchInstructionAndRun(instructions, i, Operation.JUMP);
      }

      if(testResult != null && testResult.getY()) {
        return testResult.getX();
      }
    }

    System.err.println("There was no Instruction Change that led to a full Execution of Code");
    return -1;
  }

  /**
   * Runs the Set of Instructions and returns the Accumulator Value
   *
   * @param instructions List of Instruction
   *
   * @return Tuple of Accumulator and Boolean True if Set of Instructions was successfully executed False if there would
   * have been a duplicated Execution
   */
  private Tuple<Integer, Boolean> runProgramm(List<Instruction> instructions) {
    Set<Integer> usedIndex = new HashSet<>();
    int accumulator = 0;

    for(int i = 0; i < instructions.size(); i++) {
      Instruction instruction = instructions.get(i);

      if(usedIndex.contains(i)) {
        return new Tuple<>(accumulator, false);
      }

      usedIndex.add(i);

      switch(instruction.getOperation()) {
        case ACCUMULATOR:
          accumulator += instruction.getArgument();
          break;
        case JUMP:
          i += instruction.getArgument();
          //Remove the automatic addition from the for loop
          i--;
          break;
      }
    }

    return new Tuple<>(accumulator, true);
  }

  /**
   * Changes the Set of Instructions and returns the Result of the Run with the changed Instructions
   *
   * @param instructions List of Instructions
   * @param switchIndex  Index where an Operation should be Switched
   * @param newOperation Operation that is the Replacement at the given Index
   *
   * @return Tuple of Accumulator and Boolean True if Set of Instructions was successfully executed False if there would
   * have been a duplicated Execution
   */
  private Tuple<Integer, Boolean> switchInstructionAndRun(List<Instruction> instructions, Integer switchIndex,
                                                          Operation newOperation) {
    List<Instruction> clone = new ArrayList<>(instructions);
    clone.set(switchIndex, new Instruction(newOperation, clone.get(switchIndex).getArgument()));

    return runProgramm(clone);
  }
}
