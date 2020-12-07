package me.newceptiondev.day7;

import me.newceptiondev.util.FileUtil;

import java.util.*;
import java.util.stream.Collectors;

public class Day7 {

    public static final String fileName = "day7Task1Input";
    public static final String SEARCHED_COLOR = "shiny gold";

    public static void main(String[] args) {
        List<String> inputs = FileUtil.readFileAsListOfLines(fileName);

        new Day7(inputs);
    }

    public Day7() {
    }

    public Day7(List<String> inputs) {
        System.out.println("Task 1: " + task1(inputs));
        System.out.println("Task 2: " + task2(inputs));
    }

    /**
     * Task 1
     *
     * @param inputs List of String
     * @return Count of Bag Colors that can eventually contain the searched colored Bag
     */
    public int task1(final List<String> inputs) {
        List<Bag> bags = inputs.stream().map(Bag::new).collect(Collectors.toList());

        Map<String, Set<String>> bagWithTheirParentBags = getColorsAndBagsContainingThem(bags);

        Set<String> colors = getColorsOfBagsContainingColor(SEARCHED_COLOR, new HashSet<>(),
                bagWithTheirParentBags);

        return colors.size();
    }

    /**
     * Task 2
     *
     * @param inputs List of String
     * @return Returns the Count of Bags needed within a Bag of the searched Color
     */
    public int task2(final List<String> inputs) {
        List<Bag> bags = inputs.stream().map(Bag::new).collect(Collectors.toList());

        Map<String, Bag> colorBagMap = createColorBagMapping(bags);

        return getNeededBags(SEARCHED_COLOR, colorBagMap);
    }

    /**
     * Maps all Bags to a Map which specifies the Colors of Bags containing a Color
     *
     * @param bags List of Bag
     * @return Map with a Color and all Bags that can contain that Color
     */
    private Map<String, Set<String>> getColorsAndBagsContainingThem(final List<Bag> bags) {
        Map<String, Set<String>> bagsWithParents = new HashMap<>();

        for (Bag bag : bags) {
            for (String color : bag.getContains().keySet()) {
                if (bagsWithParents.containsKey(color)) {
                    bagsWithParents.get(color).add(bag.getColor());
                } else {
                    Set<String> colors = new HashSet<>();
                    colors.add(bag.getColor());

                    bagsWithParents.put(color, colors);
                }
            }
        }

        return bagsWithParents;
    }

    /**
     * Returns all Colors of Bags that contain the given Color
     * Recursively called
     *
     * @param color          Color that should be contained by the Bags
     * @param alreadyChecked Set of Colors that were already checked to avoid multiple Runs of Colors
     * @param mappedColors   Colors with all Colors of Bags containing them
     * @return Set of Colors
     */
    private Set<String> getColorsOfBagsContainingColor(final String color, Set<String> alreadyChecked, final Map<String, Set<String>> mappedColors) {
        Set<String> parents = new HashSet<>();

        if (mappedColors.containsKey(color)) {
            for (String parent : mappedColors.get(color)) {
                if (!alreadyChecked.contains(parent)) {
                    alreadyChecked.add(parent);
                    parents.add(parent);
                    parents.addAll(
                            getColorsOfBagsContainingColor(parent, alreadyChecked, mappedColors));
                }
            }
        }

        return parents;
    }

    /**
     * Creates a Mapping between a Color and the Bag representing the Color
     *
     * @param bags List of Bag
     * @return Map with a Color and the corresponding Bag
     */
    private Map<String, Bag> createColorBagMapping(final List<Bag> bags) {
        Map<String, Bag> createColorBagMap = new HashMap<>();

        for (Bag bag : bags) {
            createColorBagMap.put(bag.getColor(), bag);
        }

        return createColorBagMap;
    }

    /**
     * Returns the Count of Bags needed within a Bag of a given Color
     * Recursively
     *
     * @param color       Color of Bag that is the Starting Point
     * @param colorBagMap Map of Colors and corresponding Bags
     * @return Count of Bags
     */
    private int getNeededBags(final String color, final Map<String, Bag> colorBagMap) {
        //Start with one as the Bag itself needs to be counted
        int neededBags = 1;

        Bag bag = colorBagMap.get(color);

        //The starting Bag does not need to be counted
        if (color.equals(SEARCHED_COLOR)) {
            neededBags = 0;
        }

        for (String neededColor : bag.getContains().keySet()) {
            neededBags += bag.getContains().get(neededColor) * getNeededBags(neededColor,
                    colorBagMap);
        }

        return neededBags;
    }
}
