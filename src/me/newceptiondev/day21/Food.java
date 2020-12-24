package me.newceptiondev.day21;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Food {

  private final Set<String> ingredients;
  private Set<String> allergens = new HashSet<>();

  /**
   * Creates Food from the description
   *
   * @param description Description of Food
   */
  public Food(final String description) {
    String[] ingredientsAndAllergensSplit = description.split("\\(");

    ingredients = parseIngredients(ingredientsAndAllergensSplit[0].trim());

    if(ingredientsAndAllergensSplit.length > 1) {
      allergens = parseAllergens(
          ingredientsAndAllergensSplit[1].substring(8, ingredientsAndAllergensSplit[1].length() - 1).trim());
    }
  }

  /**
   * Parses the Ingredients
   *
   * @param ingredientString String containing all Ingredients
   *
   * @return Set of Ingredients
   */
  private Set<String> parseIngredients(final String ingredientString) {
    String[] ingredients = ingredientString.split(" ");

    return new HashSet<>(Arrays.asList(ingredients.clone()));
  }

  /**
   * Parses the Allergens
   *
   * @param allergenString String containing all Allergens
   *
   * @return Set of Allergens
   */
  private Set<String> parseAllergens(final String allergenString) {
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
