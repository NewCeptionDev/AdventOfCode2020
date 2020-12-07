package me.newceptiondev.day7;

import java.util.HashMap;
import java.util.Map;

public class Bag {

    private final String color;
    private final Map<String, Integer> contains = new HashMap<>();

    /**
     * Creates a Bag from a Code describing it
     *
     * @param code String describing the Bag
     */
    public Bag(final String code) {
        //Splits the Code at "contain" to obtain the Color of the Bag in one Part and the contained Bags in another Part
        String[] split = code.split("contain");
        //Removes "bags" to parse the Color
        this.color = split[0].substring(0, split[0].indexOf("bags") - 1);

        //Splits the contained Bags at "," to receive a List of Contained Bags
        String[] containedBags = split[1].split(",");

        parseContainedBags(containedBags);
    }

    /**
     * Parses a List of contained Bags and adds them to the Bag
     *
     * @param containedBags List of Bags described as Count needed and Color of Bag
     */
    private void parseContainedBags(final String[] containedBags) {
        for (String bag : containedBags) {
            bag = bag.trim();

            if (!bag.contains("no other bags.")) {
                int count = Integer.parseInt(bag.substring(0, 1));
                String color = bag.substring(2, bag.indexOf("bag") - 1);

                contains.put(color, count);
            }
        }
    }

    /**
     * Getter for the Color of the Bag
     *
     * @return String Color
     */
    public String getColor() {
        return color;
    }

    /**
     * Getter for the Bags contained
     *
     * @return Map of Color and the Count of the Bag
     */
    public Map<String, Integer> getContains() {
        return contains;
    }
}
