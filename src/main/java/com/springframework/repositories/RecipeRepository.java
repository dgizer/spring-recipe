package com.springframework.repositories;

import com.springframework.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

    public Optional<Recipe> findRecipesByDescription(String description);

}
