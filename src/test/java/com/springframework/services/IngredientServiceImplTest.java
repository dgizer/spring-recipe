package com.springframework.services;

import com.springframework.commands.IngredientCommand;
import com.springframework.converters.CommandToIngredient;
import com.springframework.converters.CommandToUnitOfMeasure;
import com.springframework.converters.IngredientToCommand;
import com.springframework.converters.UnitOfMeasureToCommand;
import com.springframework.domain.Ingredient;
import com.springframework.domain.Recipe;
import com.springframework.repositories.RecipeRepository;
import com.springframework.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {
    private static final Long RECIPE_ID = 1L;
    private static final Long ING1_ID = 1L;
    private static final Long ING2_ID = 2L;
    private static final Long ING3_ID = 3L;

    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    private UnitOfMeasureRepository uomRepo;

    private IngredientToCommand ingredientToCommand;
    private CommandToIngredient commandToIngredient;

    private IngredientService ingredientService;

    IngredientServiceImplTest() {
        this.commandToIngredient = new CommandToIngredient(new CommandToUnitOfMeasure());
        this.ingredientToCommand = new IngredientToCommand(new UnitOfMeasureToCommand());
    }

    @BeforeEach
    void setUp() {
        ingredientService = new IngredientServiceImpl(recipeRepository, ingredientToCommand, uomRepo ,commandToIngredient);
    }

    @Test
    void findByRecipeIdAndIngredientId() {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);
        Ingredient ingr1 = new Ingredient();
        ingr1.setId(ING1_ID);

        Ingredient ingr2 = new Ingredient();
        ingr2.setId(ING2_ID);
        Ingredient ingr3 = new Ingredient();
        ingr3.setId(ING3_ID);
        recipe.addIngredient(ingr1);
        recipe.addIngredient(ingr2);
        recipe.addIngredient(ingr3);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        //when
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        IngredientCommand foundIngredient = ingredientService.findByRecipeIdAndIngredientId(RECIPE_ID,ING2_ID);

        //then
        assertNotNull(foundIngredient);
        assertEquals(ING2_ID,foundIngredient.getId());
        assertEquals(RECIPE_ID,foundIngredient.getRecipeId());
        verify(recipeRepository).findById(anyLong());

    }

    @Test
    void saveIngredient() {
        //given
        Long id1 = 1L;
        Long recId = 2L;
        IngredientCommand command =  new IngredientCommand();
        command.setId(id1);
        command.setRecipeId(recId);
        Optional<Recipe> recipeOpt = Optional.of(new Recipe());
        Recipe recipeSaved = new Recipe();
        recipeSaved.addIngredient(new Ingredient());
        recipeSaved.getIngredients().iterator().next().setId(id1);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOpt);
        when(recipeRepository.save(any())).thenReturn(recipeSaved);

        //when
        IngredientCommand savedComamnd = ingredientService.saveIngredient(command);

        //then
        assertNotNull(savedComamnd);
        assertEquals(id1,savedComamnd.getId());
        verify(recipeRepository).findById(anyLong());
        verify(recipeRepository).save(any(Recipe.class));
    }

    @Test
    void deleteIngredientById() {
        //given
        Long id1 = 1L;
        Long id2 = 2L;
        Long recId = 1L;

        Ingredient ingr1 = new Ingredient();
        ingr1.setId(id1);
        Ingredient ingr2 = new Ingredient();
        ingr2.setId(id2);

        Recipe recipe = new Recipe();
        recipe.addIngredient(ingr1);
        recipe.addIngredient(ingr2);
        Recipe savedRecipe = new Recipe();
        savedRecipe.setId(recId);
        savedRecipe.addIngredient(ingr2);

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

        //when
        ingredientService.deleteIngredientById(recId,id1);

        //then
        verify(recipeRepository).findById(anyLong());
        verify(recipeRepository).save(any());
        assertFalse(recipe.getIngredients().contains(ingr1));
    }
}