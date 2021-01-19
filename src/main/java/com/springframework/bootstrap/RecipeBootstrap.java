package com.springframework.bootstrap;

import com.springframework.domain.Category;
import com.springframework.domain.Ingredient;
import com.springframework.domain.Recipe;
import com.springframework.repositories.CategoryRepository;
import com.springframework.repositories.IngredientRepository;
import com.springframework.repositories.RecipeRepository;
import com.springframework.repositories.UnitOfMeasureRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class RecipeBootstrap implements CommandLineRunner {

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
    public void run(String... args) throws Exception {

       loadData();
    }

    private void loadData() {
        Set<Category> tcat = new HashSet<>();

        Recipe recipe1 = new Recipe();
        recipe1.setDescription("Guacamole");
        tcat.add(categoryRepository.findByDescription("Mexican").get());
        recipe1.setCategories(tcat);

        Set<Ingredient> ting = new HashSet<>();
        Ingredient ingr1, ingr2;
        ingr1 = ingredientRepository.findByDescription("avocado").get();
        ingr1.setUom(unitOfMeasureRepository.findByDescription("ripe").get());
        ingr2 = ingredientRepository.findByDescription("salt").get();
        ingr2.setUom(unitOfMeasureRepository.findByDescription("Teaspoon").get());

        ting.add(ingr1);
        ting.add(ingr2);
        //recipe1.setIngredients(ting);
        recipeRepository.save(recipe1);

        Recipe recipe2 = new Recipe();
        recipe2.setDescription("Chicken Tacos");
       /* recipe2.getCategories().add(categoryRepository.findByDescription("Mexican").get());

        ingr1 = ingredientRepository.findByDescription("tomato").get();
        ingr1.setUom(unitOfMeasureRepository.findByDescription("Pinch").get());
        recipe2.getIngredients().add(ingr1);*/

        recipeRepository.save(recipe2);





    }

}
