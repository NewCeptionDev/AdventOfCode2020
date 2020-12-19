package me.newceptiondev.day19;

import me.newceptiondev.util.FileUtil;

import java.util.*;

public class Day19 {

    public static final String fileName = "day19Task1Input";

    public static void main(String[] args) {
        List<String> inputs = FileUtil.readFileAsListOfLines(fileName);

        new Day19(inputs);
    }

    public Day19() {
    }

    public Day19(List<String> inputs) {
        System.out.println("Task 1: " + task1(inputs));
        System.out.println("Task 2: " + task2(inputs));
    }

    /**
     * Task 1
     *
     * @param inputs List of String
     * @return
     */
    public long task1(List<String> inputs) {
        Map<Integer, Rule> rules = new HashMap<>();
        List<String> words = new ArrayList<>();

        boolean ruleParsing = true;

        for(String input : inputs){
            if(input.equals("")){
                ruleParsing = false;
            }

            if(ruleParsing){
                Rule rule = new Rule(input);
                rules.put(rule.getId(), rule);
            } else {
                words.add(input);
            }
        }

        Set<String> validWords = rules.get(0).buildPossibleLiterals(rules);

        return words.stream().filter(validWords::contains).count();
    }

    /**
     * Task 2
     *
     * @param inputs List of String
     * @return
     */
    public int task2(List<String> inputs) {
        return 0;
    }
}
