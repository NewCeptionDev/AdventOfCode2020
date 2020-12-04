package day2;

import util.FileUtil;

import java.util.List;

public class Day2 {

    public static void main(String[] args) {
        new Day2();
    }

    public Day2() {
        List<String> inputs = FileUtil.readFileAsListOfLines("day2Task1Input");

        System.out.println("Task 1: " + task1(inputs));
        System.out.println("Task 2: " + task2(inputs));
    }

    /**
     * Task 1
     *
     * @param inputs List of String
     * @return Count of valid Passwords regarding the Requirements of Task 1
     */
    public int task1(final List<String> inputs){
        int validPasswords = 0;

        for(String input : inputs){
            ParsedInput parsedInput = parseInput(input);

            if(parsedInput.isValidForTask1()){
                validPasswords++;
            }
        }

        return validPasswords;
    }

    /**
     * Task 2
     *
     * @param inputs List of String
     * @return Count of valid Passwords regarding the Requirements of Task 2
     */
    public int task2(final List<String> inputs){
        int validPasswords = 0;

        for(String input : inputs){
            ParsedInput parsedInput = parseInput(input);

            if(parsedInput.isValidForTask2()){
                validPasswords++;
            }
        }

        return validPasswords;
    }

    /**
     * Parses the Input to a ParsedInput Object
     *
     * @param input String that should be parsed
     * @return ParsedInput
     */
    private ParsedInput parseInput(final String input){
        //Using Split as I do not need to worry about the Letter Counts with this
        String[] informationPasswordSplit = input.split(":");
        String[] rangeLetterSplit = informationPasswordSplit[0].split(" ");
        String[] rangeSplit = rangeLetterSplit[0].split("-");

        String password = informationPasswordSplit[1].trim();
        char requiredCharacter = rangeLetterSplit[1].charAt(0);
        int lowerRangeEnd = Integer.parseInt(rangeSplit[0]);
        int upperRangeEnd = Integer.parseInt(rangeSplit[1]);

        return new ParsedInput(password, requiredCharacter, lowerRangeEnd, upperRangeEnd);
    }

}
