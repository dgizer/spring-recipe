package com.springframework.controllers;

import com.springframework.domain.Category;
import com.springframework.domain.UnitOfMeasure;
import com.springframework.repositories.CategoryRepository;
import com.springframework.repositories.RecipeRepository;
import com.springframework.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class IndexController {

    private final CategoryRepository categoryRepo;
    private final UnitOfMeasureRepository unitOfMeasureRepo;
    private final RecipeRepository recipeRepository;

    public IndexController(CategoryRepository categoryRepo,
                           UnitOfMeasureRepository unitOfMeasureRepo,
                           RecipeRepository recipeRepository) {
        this.categoryRepo = categoryRepo;
        this.unitOfMeasureRepo = unitOfMeasureRepo;
        this.recipeRepository = recipeRepository;
    }

    @RequestMapping({"","/", "/index","/index.html"})
    public String getIndexPage(Model model){

        Optional<Category> categoryOptional = categoryRepo.findByDescription("American");
        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepo.findByDescription("Teaspoon");

        System.out.println("Cat id is: " + categoryOptional.get().getId());
        System.out.println("Unit of measure id is: " + unitOfMeasureOptional.get().getId());

        model.addAttribute("recipes", recipeRepository.findAll());

        return "index";
    }
}
