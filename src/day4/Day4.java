package day4;

import util.FileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day4 {

    public static final String fileName = "day4Task1Input";

    public static void main(String[] args) {
        new Day4();
    }

    public Day4() {
        List<String> inputs = FileUtil.readFileAsListOfLines(fileName);

        System.out.println("Task 1: " + task1(inputs));
        System.out.println("Task 2: " + task2(inputs));
    }

    /**
     * Task 1
     *
     * @param inputs List of String
     * @return Number of valid Passports regarding the Requirements of Task 1
     */
    public long task1(final List<String> inputs) {
        List<Passport> passports = mapInputToPassports(inputs);

        return passports.stream().filter(Passport::isValidForTask1).count();
    }

    /**
     * Task 2
     *
     * @param inputs List of String
     * @return Number of valid Passports regarding the Requirements of Task 2
     */
    public long task2(final List<String> inputs) {

        List<Passport> passports = mapInputToPassports(inputs);

        return passports.stream().filter(Passport::isValidForTask2).count();
    }

    /**
     * Maps the Inputs to Passports
     *
     * @param inputs List of String
     * @return List of Passports
     */
    private List<Passport> mapInputToPassports(final List<String> inputs) {
        List<String> passportStrings = separatePassportInputs(inputs);

        return passportStrings.stream().map(Passport::new).collect(Collectors.toList());
    }

    /**
     * Maps the List of Lines to a List of Passport Strings
     *
     * @param inputs List of String with each String being one Line from the Inputfile
     * @return List of String with each String containing all Information about one Passport
     */
    private List<String> separatePassportInputs(final List<String> inputs) {
        List<String> passportInputs = new ArrayList<>();

        StringBuilder currentPassportInput = new StringBuilder();

        for (String input : inputs) {
            if (input.length() > 0) {
                currentPassportInput.append(input).append(" ");
            } else {
                passportInputs.add(currentPassportInput.deleteCharAt(
                        currentPassportInput.length() - 1).toString());
                //Clears the StringBuilders Buffer
                currentPassportInput.setLength(0);
            }
        }
        //Adding the last Passport
        passportInputs.add(
                currentPassportInput.deleteCharAt(currentPassportInput.length() - 1).toString());

        return passportInputs;
    }
}
