package me.newceptiondev.day18;

import me.newceptiondev.util.FileUtil;

import java.math.BigInteger;
import java.util.*;

public class Day18 {

    private static final String FILE_NAME = "day18Task1Input";

    public static void main(String[] args) {
        List<String> inputs = FileUtil.readFileAsListOfLines(FILE_NAME);

        new Day18(inputs);
    }

    public Day18() {
    }

    private Day18(List<String> inputs) {
        System.out.println("Task 1: " + task1(inputs));
        System.out.println("Task 2: " + task2(inputs));
    }

    /**
     * Task 1
     *
     * @param inputs List of String
     *
     * @return Evaluation Result of Expression
     */
    public long task1(List<String> inputs) {

        return inputs.stream().map(this::evaluate).reduce(Long::sum).get();
    }

    /**
     * Task 2
     *
     * @param inputs List of String
     *
     * @return
     */
    public BigInteger task2(List<String> inputs) {
        return inputs.stream().map(this::reduceLine).reduce(BigInteger::add).get();
    }

    private long evaluate(String code) {

        Operation currentOperation = null;
        Long accumulator = null;
        Long summand = null;

        while(code.length() > 0) {
            if(code.charAt(0) == '(') {
                long result = evaluate(code.substring(1));

                code = code.substring(findCorrespondingParenthesesIndex(code));

                if(accumulator != null) {
                    summand = result;
                } else {
                    accumulator = result;
                }
            } else if(code.charAt(0) == ' ') {
                //DO Nothing
            } else if(code.charAt(0) == '+') {
                currentOperation = Operation.ADDITION;
            } else if(code.charAt(0) == '*') {
                currentOperation = Operation.MULTIPLICATION;
            } else if(code.charAt(0) == ')') {
                return accumulator;
            } else {
                long result = Integer.parseInt(code.charAt(0) + "");

                if(accumulator != null) {
                    summand = result;
                } else {
                    accumulator = result;
                }
            }

            if(accumulator != null && summand != null) {
                if(currentOperation == Operation.MULTIPLICATION) {
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

    private int findCorrespondingParenthesesIndex(String input) {
        int currentLevel = 1;

        int currentIndex = 0;
        while(currentLevel > 0 && input.length() > currentIndex) {
            currentIndex++;

            if(input.charAt(currentIndex) == '(') {
                currentLevel++;
            } else if(input.charAt(currentIndex) == ')') {
                currentLevel--;
            }
        }

        return currentIndex;
    }

    private BigInteger reduceLine(String input) {
        String editString = input;
        Deque<Integer> openParenthesisPosition = new ArrayDeque<>();

        for(int i = 0; i < editString.length(); i++) {
            if(editString.charAt(i) == '(') {
                openParenthesisPosition.push(i);
            } else if(editString.charAt(i) == ')') {
                int correspondingOpenParenthesis = openParenthesisPosition.pop();
                editString = editString.substring(0, correspondingOpenParenthesis) + calculateResult(
                    editString.substring(correspondingOpenParenthesis + 1, i)) + editString.substring(i + 1);
                i = correspondingOpenParenthesis - 1;
            }
        }

        return calculateResult(editString);
    }

    private BigInteger calculateResult(String input) {
        Deque<BigInteger> numbers = new ArrayDeque<>();

        boolean additionAfterNextNumber = false;
        StringBuilder currentNumber = new StringBuilder();
        for(int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);
            if(Character.isDigit(currentChar)) {
                currentNumber.append(currentChar);
            } else if(currentChar == ' ') {
                if(!currentNumber.isEmpty()) {
                    BigInteger parsed = new BigInteger(currentNumber.toString());

                    if(additionAfterNextNumber) {
                        BigInteger first = numbers.pop();
                        numbers.push(first.add(parsed));
                        additionAfterNextNumber = false;
                    } else {
                        numbers.push(parsed);
                    }
                    currentNumber = new StringBuilder();
                }
            } else if(currentChar == '+') {
                additionAfterNextNumber = true;
            }
        }

        BigInteger parsed = new BigInteger(currentNumber.toString());

        if(additionAfterNextNumber) {
            BigInteger first = numbers.poll();
            numbers.push(first.add(parsed));
        } else {
            numbers.push(parsed);
        }

        return numbers.stream().reduce(BigInteger.valueOf(1L), BigInteger::multiply);
    }
}
