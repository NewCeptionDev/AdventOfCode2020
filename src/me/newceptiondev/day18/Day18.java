package me.newceptiondev.day18;

import me.newceptiondev.util.FileUtil;

import java.util.List;

public class Day18 {

    public static final String fileName = "day18Task1Input";

    public static void main(String[] args) {
        List<String> inputs = FileUtil.readFileAsListOfLines(fileName);

        new Day18(inputs);
    }

    public Day18() {
    }

    public Day18(List<String> inputs) {
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

        return inputs.stream().map(this::evaluate).reduce(Long::sum).get();
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

    private long evaluate(String code){

        Operation currentOperation = null;
        Long accumulator = null;
        Long summand = null;

        while(code.length() > 0) {
            if (code.charAt(0) == '(') {
                long result = evaluate(code.substring(1));

                code = code.substring(findCorrespondingParenthesesIndex(code));

                if (accumulator != null) {
                    summand = result;
                } else {
                    accumulator = result;
                }
            } else if (code.charAt(0) == ' ') {
                //DO Nothing
            } else if (code.charAt(0) == '+') {
                currentOperation = Operation.ADDITION;
            } else if (code.charAt(0) == '*') {
                currentOperation = Operation.MULTIPLICATION;
            } else if (code.charAt(0) == ')') {
                return accumulator;
            } else {
                long result = Integer.parseInt(code.charAt(0) + "");

                if (accumulator != null) {
                    summand = result;
                } else {
                    accumulator = result;
                }
            }

            if (accumulator != null && summand != null) {
                if (currentOperation == Operation.MULTIPLICATION) {
                    accumulator *= summand;
                } else {
                    accumulator += summand;
                }
                summand = null;
            }
            code = code.substring(1);
        }

        return accumulator;
    }

    private int findCorrespondingParenthesesIndex(String input){
        int currentLevel = 1;

        int currentIndex = 0;
        while (currentLevel > 0 && input.length() > currentIndex){
            currentIndex++;

            if(input.charAt(currentIndex) == '('){
                currentLevel++;
            } else if(input.charAt(currentIndex) == ')'){
                currentLevel--;
            }
        }

        return currentIndex;
    }
}
