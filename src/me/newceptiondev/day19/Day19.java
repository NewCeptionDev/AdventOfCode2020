package me.newceptiondev.day19;

import me.newceptiondev.util.FileUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day19 {

  private static final String FILE_NAME_TASK_1 = "day19Task1Input";
  private static final String FILE_NAME_TASK_2 = "day19Task2Input";

  public static void main(String[] args) {
    List<String> inputsTask1 = FileUtil.readFileAsListOfLines(FILE_NAME_TASK_1);
    List<String> inputsTask2 = FileUtil.readFileAsListOfLines(FILE_NAME_TASK_2);

    new Day19(inputsTask1, inputsTask2);
  }

  public Day19() {
  }

  private Day19(List<String> inputsTask1, List<String> inputsTask2) {
    System.out.println("Task 1: " + task1(inputsTask1));
    System.out.println("Task 2: " + task2(inputsTask2));
  }

  /**
   * Task 1
   *
   * @param inputs List of String
   *
   * @return Count of String matching Rule 0
   */
  public long task1(List<String> inputs) {
    Map<Integer, Rule> rules = new HashMap<>();
    List<String> words = new ArrayList<>();

    boolean ruleParsing = true;

    for(String input : inputs) {
      if(input.isEmpty()) {
        ruleParsing = false;
      }

      if(ruleParsing) {
        Rule rule = new Rule(input);
        rules.put(rule.getId(), rule);
      } else {
        words.add(input);
      }
    }

    String regex = "^" + rules.get(0).buildRegex(rules) + "$";

    return words.stream().filter(s -> s.matches(regex)).count();
  }

  /**
   * Task 2
   *
   * @param inputs List of String
   *
   * @return Count of String matching Rule 0
   */
  public long task2(List<String> inputs) {
    Map<Integer, Rule> rules = new HashMap<>();
    List<String> words = new ArrayList<>();

    boolean ruleParsing = true;

    for(String input : inputs) {
      if(input.isEmpty()) {
        ruleParsing = false;
      }

      if(ruleParsing) {
        Rule rule = new Rule(input);
        rules.put(rule.getId(), rule);
      } else {
        words.add(input);
      }
    }

    String regex = "^" + rules.get(0).buildRegex(rules) + "$";

    return words.stream().filter(s -> s.matches(regex)).count();
  }
}
