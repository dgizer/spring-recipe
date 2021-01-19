package com.springframework.bootstrap;

import com.springframework.domain.*;
import com.springframework.enums.Difficulty;
import com.springframework.repositories.CategoryRepository;
import com.springframework.repositories.IngredientRepository;
import com.springframework.repositories.RecipeRepository;
import com.springframework.repositories.UnitOfMeasureRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {
    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final IngredientRepository ingredientRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public RecipeBootstrap(RecipeRepository recipeRepository,
                           CategoryRepository categoryRepository,
                           IngredientRepository ingredientRepository,
                           UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.ingredientRepository = ingredientRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        recipeRepository.saveAll(getRecipes());
    }

    private List<Recipe> getRecipes() {
        ArrayList<Recipe> recipes = new ArrayList<>(2);
        Recipe recipe1 = new Recipe();
        recipe1.setDescription("Guacamole");
        recipe1.setDirections("1 do first " +
                "2 do seconf" +
                "3 do anything else...");
        recipe1.setPrepTime(20);
        recipe1.setCookTime(0);
        recipe1.setServings(5);
        recipe1.setDifficulty(Difficulty.EASY);
        Notes notes1 = new Notes();
        notes1.setRecipeNotes("Please take into consideration this notes");
        recipe1.setNotes(notes1);
        notes1.setRecipe(recipe1);

        Ingredient ingr1;

        ingr1 = getIngfromOpt(ingredientRepository.findByDescription("avocado"));
        ingr1.setUom(getUomfromOpt(unitOfMeasureRepository.findByDescription("Each")));
        ingr1.setAmount(new BigDecimal(3));
        ingr1.setRecipe(recipe1);
        recipe1.getIngredients().add(ingr1);

        Category catMex = getCatfromOpt(categoryRepository.findByDescription("Mexican"));
        Category catAm = getCatfromOpt(categoryRepository.findByDescription("American"));
        recipe1.getCategories().add(catMex);
        recipe1.getCategories().add(catAm);




        /*Ingredient ingr2;
        ingr2 = getIngfromOpt(ingredientRepository.findByDescription("salt"));
        ingr2.setUom(getUomfromOpt(unitOfMeasureRepository.findByDescription("Teaspoon")));
        ingr2.setAmount(new BigDecimal(0.5));
        recipe1.getIngredients().add(ingr2);
        ingr2.setRecipe(recipe1);*/

        recipes.add(recipe1);

        /*Recipe recipe2 = new Recipe();
        recipe2.setDescription("Chicken Tacos");
        /*recipe2.getCategories().add(categoryRepository.findByDescription("Mexican").get());

        ingr1 = ingredientRepository.findByDescription("tomato").get();
        ingr1.setUom(unitOfMeasureRepository.findByDescription("Pinch").get());
        recipe2.getIngredients().add(ingr1);* /

        recipes.add(recipe2);*/

        return recipes;

    }

    private Category getCatfromOpt(Optional<Category> catOpt) {
        if (catOpt.isPresent()) {
            return catOpt.get();
        } else {
            throw new RuntimeException("Category not found");
        }
    }

    private Ingredient getIngfromOpt(Optional<Ingredient> ingrOpt) {
        if (ingrOpt.isPresent()) {
            return ingrOpt.get();
        } else {
            throw new RuntimeException("Ingredient not found");
        }
    }

    private UnitOfMeasure getUomfromOpt(Optional<UnitOfMeasure> uomOpt) {
        if (uomOpt.isPresent()) {
            return uomOpt.get();
        } else {
            throw new RuntimeException("Unit of measure not found");
        }
    }



}
