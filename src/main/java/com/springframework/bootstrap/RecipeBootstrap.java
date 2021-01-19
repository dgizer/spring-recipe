package com.springframework.bootstrap;

import com.springframework.domain.Category;
import com.springframework.domain.Ingredient;
import com.springframework.domain.Recipe;
import com.springframework.domain.UnitOfMeasure;
import com.springframework.repositories.CategoryRepository;
import com.springframework.repositories.IngredientRepository;
import com.springframework.repositories.RecipeRepository;
import com.springframework.repositories.UnitOfMeasureRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
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

        Recipe recipe1 = new Recipe();
        recipe1.setDescription("Guacamole");
        recipe1.getCategories().add(getCatfromOpt(categoryRepository.findByDescription("Mexican")));

        UnitOfMeasure uom = new UnitOfMeasure();
        Ingredient ingr1;

        ingr1 = getIngfromOpt(ingredientRepository.findByDescription("avocado"));
        ingr1.setUom(getUomfromOpt(unitOfMeasureRepository.findByDescription("Each")));
        ingr1.setAmount(new BigDecimal(3));
        recipe1.getIngredients().add(ingr1);
        ingr1.setRecipe(recipe1);

        Ingredient ingr2;
        ingr2 = getIngfromOpt(ingredientRepository.findByDescription("salt"));
        ingr2.setUom(getUomfromOpt(unitOfMeasureRepository.findByDescription("Teaspoon")));
        ingr2.setAmount(new BigDecimal(0.5));
        recipe1.getIngredients().add(ingr2);
        ingr2.setRecipe(recipe1);

        recipeRepository.save(recipe1);


        /*Recipe recipe2 = new Recipe();
        recipe2.setDescription("Chicken Tacos");
        recipe2.getCategories().add(categoryRepository.findByDescription("Mexican").get());

        ingr1 = ingredientRepository.findByDescription("tomato").get();
        ingr1.setUom(unitOfMeasureRepository.findByDescription("Pinch").get());
        recipe2.getIngredients().add(ingr1);

        recipeRepository.save(recipe2);*/





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
