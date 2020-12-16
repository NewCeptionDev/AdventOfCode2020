package me.newceptiondev.day16;

public class ValidationRange {

    private final int minimum;
    private final int maximum;

    /**
     * Creates a ValidationRange from a description
     *
     * @param description Description
     */
    public ValidationRange(String description){
        String[] parts = description.split("-");

        this.minimum = Integer.parseInt(parts[0]);
        this.maximum = Integer.parseInt(parts[1]);
    }

    /**
     * Checks whether the given Number is within the Range
     *
     * @param number Number to be checked
     * @return True if Number is within Range
     */
    public boolean isValid(int number){
        return minimum <= number && maximum >= number;
    }
}
