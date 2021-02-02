package com.springframework.controllers;

import com.springframework.services.ImageService;
import com.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@Controller
public class ImageController {
    private final ImageService imageService;
    private final RecipeService recipeService;

    public ImageController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{recipeId}/image")
    public String getImage(@PathVariable String recipeId, Model model){
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(recipeId)));
        log.debug("fund recipe id" + recipeId);
        return "recipe/show";
    }
}
