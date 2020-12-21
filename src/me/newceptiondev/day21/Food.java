package me.newceptiondev.day21;

import java.util.*;

public class Food {

    private Set<String> ingredients = new HashSet<>();
    private Set<String> allergens = new HashSet<>();

    public Food(String description) {
        String[] ingredientsAndAllergensSplit = description.split("\\(");

        this.ingredients = parseIngredients(ingredientsAndAllergensSplit[0].trim());

        if (ingredientsAndAllergensSplit.length > 1) {
            this.allergens = parseAllergens(ingredientsAndAllergensSplit[1]
                    .substring(8, ingredientsAndAllergensSplit[1].length() - 1).trim());
        }
    }

    private Set<String> parseIngredients(String ingredientString) {
        String[] ingredients = ingredientString.split(" ");

        return new HashSet<>(Arrays.asList(ingredients.clone()));
    }

    private Set<String> parseAllergens(String allergenString) {
        String[] allergens = allergenString.split(", ");

        return new HashSet<>(Arrays.asList(allergens.clone()));
    }

    public Set<String> getIngredients() {
        return ingredients;
    }

    public Set<String> getAllergens() {
        return allergens;
    }
}
