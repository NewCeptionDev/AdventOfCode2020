package me.newceptiondev.day21;

import me.newceptiondev.util.FileUtil;

import java.util.*;
import java.util.stream.Collectors;

public class Day21 {

    public static final String fileName = "day21Task1Input";

    public static void main(String[] args) {
        List<String> inputs = FileUtil.readFileAsListOfLines(fileName);

        new Day21(inputs);
    }

    public Day21() {
    }

    public Day21(List<String> inputs) {
        System.out.println("Task 1: " + task1(inputs));
        System.out.println("Task 2: " + task2(inputs));
    }

    /**
     * Task 1
     *
     * @param inputs List of String
     * @return Appearance Count of Ingredients without an Allergen
     */
    public int task1(final List<String> inputs) {
        List<Food> foods = inputs.stream().map(Food::new).collect(Collectors.toList());

        Map<String, Set<String>> allergensWithPossibleIngredients =
                mapAllergensToIngredients(foods);

        Set<String> allIngredients =
                foods.stream().map(Food::getIngredients).reduce((firstSet, secondSet) -> {
                    Set<String> combined = new HashSet<>(firstSet);
                    combined.addAll(secondSet);
                    return combined;
                }).get();

        Set<String> ingredientsWithoutAllergen = allIngredients.stream().filter(ingredient -> {
            boolean hasAllergen = false;

            for (Set<String> ingredients : allergensWithPossibleIngredients.values()) {
                if (ingredients.contains(ingredient)) {
                    hasAllergen = true;
                    break;
                }
            }

            return !hasAllergen;
        }).collect(Collectors.toSet());

        return countIngredientWithoutAllergenAppearances(foods, ingredientsWithoutAllergen);
    }

    /**
     * Task 2
     *
     * @param inputs List of String
     * @return
     */
    public String task2(final List<String> inputs) {
        List<Food> foods = inputs.stream().map(Food::new).collect(Collectors.toList());

        Map<String, Set<String>> allergensWithPossibleIngredients =
                mapAllergensToIngredients(foods);

        Map<String, String> allergensWithIngredients = mapIngredientsToAllergens(allergensWithPossibleIngredients);

        List<String> allergens = new ArrayList<>(allergensWithIngredients.keySet());
        allergens.sort(String::compareTo);

        return buildCanonicalDangerousIngredientList(allergens, allergensWithIngredients);
    }

    private Map<String, Set<String>> mapAllergensToIngredients(List<Food> foods) {
        Map<String, Set<String>> ingredientsWithPossibleAllergens = new HashMap<>();

        for (Food food : foods) {
            for (String allergen : food.getAllergens()) {
                if (!ingredientsWithPossibleAllergens.containsKey(allergen)) {
                    ingredientsWithPossibleAllergens
                            .put(allergen, new HashSet<>(food.getIngredients()));
                } else {
                    ingredientsWithPossibleAllergens.get(allergen).retainAll(food.getIngredients());
                }
            }
        }

        return ingredientsWithPossibleAllergens;
    }

    private int countIngredientWithoutAllergenAppearances(List<Food> foods,
            Set<String> allIngredientsWithoutAllergens) {
        int ingredientsWithoutAllergenAppearance = 0;

        for (Food food : foods) {
            Set<String> ingredientsWithoutAllergen = new HashSet<>(food.getIngredients());
            ingredientsWithoutAllergen.retainAll(allIngredientsWithoutAllergens);

            ingredientsWithoutAllergenAppearance += ingredientsWithoutAllergen.size();
        }

        return ingredientsWithoutAllergenAppearance;
    }

    private Map<String, String> mapIngredientsToAllergens(
            Map<String, Set<String>> allergensWithPossibleIngredients) {

        Map<String, String> allergensWithIngredients = new HashMap<>();

        Map<String, Set<String>> modifiedAllergensWithIngredients =
                new HashMap<>(allergensWithPossibleIngredients);

        while (modifiedAllergensWithIngredients.keySet().size() > 0) {
            Set<String> allergensWithOneIngredient = new HashSet<>();

            for(String allergen : modifiedAllergensWithIngredients.keySet()) {
                if(modifiedAllergensWithIngredients.get(allergen).size() == 1) {
                    allergensWithOneIngredient.add(allergen);
                }
            }

            for (String allergen : allergensWithOneIngredient) {
                allergensWithIngredients.put(allergen,
                        modifiedAllergensWithIngredients.get(allergen).stream()
                                .reduce(String::concat).get());
            }

            modifiedAllergensWithIngredients = updateAllergensWithIngredients(modifiedAllergensWithIngredients, allergensWithIngredients);
        }

        return allergensWithIngredients;
    }

    private Map<String, Set<String>> updateAllergensWithIngredients(Map<String, Set<String>> modifiedAllergensWithIngredients, Map<String, String> allergensWithIngredients) {
        Map<String, Set<String>> updated = new HashMap<>(modifiedAllergensWithIngredients);

        for(String allergen : allergensWithIngredients.keySet()) {
            updated.remove(allergen);
        }

        for(String allergen : updated.keySet()) {
            for(String alreadyUsedAllergen : allergensWithIngredients.keySet()){
                updated.get(allergen).remove(allergensWithIngredients.get(alreadyUsedAllergen));
            }
        }

        return updated;
    }

    private String buildCanonicalDangerousIngredientList(List<String> allergensOrder, Map<String, String> allergensWithIngredients) {
        StringBuilder builder = new StringBuilder();

        for(String allergen : allergensOrder) {
            builder.append(allergensWithIngredients.get(allergen)).append(",");
        }

        builder.deleteCharAt(builder.length() - 1);

        return builder.toString();
    }
}
