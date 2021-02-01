package com.springframework.services;

import com.springframework.commands.IngredientCommand;

public interface IngredientService {
    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
    IngredientCommand saveIngredient(IngredientCommand command);
    void deleteIngredientById(Long recId, Long id);
}
