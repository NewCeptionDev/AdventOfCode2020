package me.newceptiondev.day14;

import me.newceptiondev.util.FileUtil;
import me.newceptiondev.util.Tuple;

import java.util.*;

public class Day14 {

    public static final String fileName = "day14Task1Input";
    public static final int ARRAY_LENGTH = 36;

    public static void main(String[] args) {
        List<String> inputs = FileUtil.readFileAsListOfLines(fileName);

        new Day14(inputs);
    }

    public Day14() {
    }

    public Day14(List<String> inputs) {
        System.out.println("Task 1: " + task1(inputs));
        System.out.println("Task 2: " + task2(inputs));
    }

    /**
     * Task 1
     *
     * @param inputs List of String
     * @return Sum of all Values in Memory
     */
    public long task1(List<String> inputs) {
        Integer[] bitmask = new Integer[36];
        Map<Integer, Integer[]> memory = new HashMap<>();

        for (String input : inputs) {
            if (input.startsWith("mask")) {
                bitmask = parseBitMask(input);
            } else {
                Tuple<Integer, Integer[]> memoryAddressAndValue = applyMaskToValueForTask1(bitmask,
                        input);

                memory.put(memoryAddressAndValue.getX(), memoryAddressAndValue.getY());
            }
        }

        return memory.values().stream().map(this::convertBitArrayToLong).reduce(Long::sum).get();
    }

    /**
     * Task 2
     *
     * @param inputs List of String
     * @return Sum of all Values in Memory
     */
    public long task2(List<String> inputs) {
        Integer[] bitmask = new Integer[36];
        Map<Long, Long> memory = new HashMap<>();

        for (String input : inputs) {
            if (input.startsWith("mask")) {
                bitmask = parseBitMask(input);
            } else {
                Tuple<List<Integer[]>, Long> memoryAddressAndValue = applyMaskToValueForTask2(
                        bitmask,
                        input);

                for (int i = 0; i < memoryAddressAndValue.getX().size(); i++) {
                    memory.put(convertBitArrayToLong(memoryAddressAndValue.getX().get(i)),
                            memoryAddressAndValue.getY());
                }
            }
        }

        return memory.values().stream().reduce(Long::sum).get();
    }

    /**
     * Parses a Bitmask from a given Input
     *
     * @param input String
     * @return Integer[] describing the Bitmask
     */
    private Integer[] parseBitMask(String input) {
        String bitmask = input.substring(input.indexOf("=") + 2);

        char[] bitmaskChars = bitmask.toCharArray();

        return mapCharsToInt(bitmaskChars, false);
    }

    /**
     * Maps a char[] to an Integer[]
     * Can fill unused Elements with 0 or null
     *
     * @param chars char Array
     * @param fillWithZero Flag if empty Elements should be filled with 0
     * @return Integer[]
     */
    private Integer[] mapCharsToInt(char[] chars, boolean fillWithZero) {
        Integer[] result = new Integer[ARRAY_LENGTH];

        for (int i = ARRAY_LENGTH - 1; i >= 0; i--) {
            int realIndex = (ARRAY_LENGTH - i);

            if (chars.length >= realIndex && (chars[chars.length - realIndex] == '0' || chars[chars.length - realIndex] == '1')) {
                result[i] = Integer.parseInt(chars[chars.length - realIndex] + "");
            } else if (fillWithZero) {
                result[i] = 0;
            }
        }

        return result;
    }

    /**
     * Applies the given Bitmask to the given Value according to the Requirements of Task 1
     *
     * @param bitmask Integer[] Bitmask
     * @param value String Value
     * @return Tuple with MemoryAddress and Value
     */
    private Tuple<Integer, Integer[]> applyMaskToValueForTask1(Integer[] bitmask, String value) {

        Tuple<Integer, Integer[]> memoryAddressAndParsedValue = parseMemoryValue(value);

        for (int i = 0; i < bitmask.length; i++) {
            if (bitmask[i] != null) {
                memoryAddressAndParsedValue.getY()[i] = bitmask[i];
            }
        }

        return memoryAddressAndParsedValue;

    }

    /**
     * Applies the given Bitmask to the given Address according to the Requirements of Task 2
     *
     * @param bitmask Integer[] bitmask
     * @param value String Value
     * @return Tuple with List of MemoryAddress and Value
     */
    private Tuple<List<Integer[]>, Long> applyMaskToValueForTask2(Integer[] bitmask, String value) {

        Tuple<Integer[], Long> memoryAddressAndParsedValue = parseMemoryAddress(value);

        Integer[] bitmaskApplied = memoryAddressAndParsedValue.getX().clone();

        for (int i = 0; i < bitmask.length; i++) {
            if (bitmask[i] == null || bitmask[i] == 1) {
                bitmaskApplied[i] = bitmask[i];
            }
        }

        return new Tuple<>(generatePossibleMemoryAddress(bitmaskApplied),
                memoryAddressAndParsedValue.getY());
    }

    /**
     * Parses the MemoryAddress and MemoryValue from an Input
     *
     * @param input String
     * @return Tuple of MemoryAddress and MemoryValue as Integer[]
     */
    private Tuple<Integer, Integer[]> parseMemoryValue(String input) {

        int memoryAddress = Integer.parseInt(
                input.substring(input.indexOf("[") + 1, input.indexOf("]")));
        long memoryValue = Long.parseLong(input.substring(input.indexOf("=") + 2));
        String binaryValue = Long.toBinaryString(memoryValue);

        return new Tuple<>(memoryAddress,
                mapCharsToInt(binaryValue.toCharArray(), true));
    }

    /**
     * Parses the MemoryAddress and MemoryValue from an Input
     *
     * @param input String
     * @return Tuple of MemoryAddress as Integer[] and MemoryValue
     */
    private Tuple<Integer[], Long> parseMemoryAddress(String input) {

        int memoryAddress = Integer.parseInt(
                input.substring(input.indexOf("[") + 1, input.indexOf("]")));
        long memoryValue = Long.parseLong(input.substring(input.indexOf("=") + 2));
        String binaryAddress = Long.toBinaryString(memoryAddress);

        return new Tuple<>(mapCharsToInt(binaryAddress.toCharArray(), true),
                memoryValue);
    }

    /**
     * Converts a Integer[] to a Long
     *
     * @param bits Integer[]
     * @return Long represented by the given Integer[]
     */
    private Long convertBitArrayToLong(Integer[] bits) {
        String bitsRepresentation = Arrays.stream(bits).map(String::valueOf).reduce(
                String::concat).get();

        return Long.parseLong(bitsRepresentation, 2);
    }

    /**
     * Generates all Possible MemoryAddress
     *
     * @param baseValue Integer[] Containing fixed Values and null for floating Values
     * @return List of Integer[] describing all possible
     */
    private List<Integer[]> generatePossibleMemoryAddress(Integer[] baseValue) {
        List<Integer[]> possibleAddress = new ArrayList<>();

        int floatingValues = getFloatingValuesCount(baseValue);

        int possibilities = (int) Math.pow(2, floatingValues);

        for (int i = 0; i < possibilities; i++) {
            possibleAddress.add(buildModifiedVersion(baseValue, floatingValues, i));
        }

        return possibleAddress;
    }

    /**
     * Builds a modified Version of a given Integer[]
     *
     * @param baseValue Integer[] with Given base Version
     * @param floatingValues Integer Count of null Elements within baseValue
     * @param currentPossibility Integer Currently build Possibility
     * @return Integer[] Modified Version of baseValue
     */
    private Integer[] buildModifiedVersion(Integer[] baseValue, int floatingValues, int currentPossibility) {
        Integer[] modified = baseValue.clone();

        int floatingIndex = 0;
        for (int j = 0; j < modified.length; j++) {
            if (modified[j] == null) {
                int currentPossibilities = (int) Math.pow(2, (floatingValues - floatingIndex));

                if (currentPossibility % currentPossibilities < currentPossibilities / 2) {
                    modified[j] = 0;
                } else {
                    modified[j] = 1;
                }

                floatingIndex++;
            }
        }
        return modified;
    }

    /**
     * Returns the Count of null Elements within the given Array
     *
     * @param baseValue Integer[]
     * @return Count of null Elements
     */
    private int getFloatingValuesCount(Integer[] baseValue) {
        int result = 0;

        for (Integer integer : baseValue) {
            if (integer == null) {
                result++;
            }
        }
        return result;
    }
}
