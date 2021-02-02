package com.springframework.controllers;

import com.springframework.services.ImageService;
import com.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
    public String getImageForm(@PathVariable String recipeId, Model model){
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(recipeId)));
        log.debug("found recipe id" + recipeId);
        return "recipe/imageuploadform";
    }

    @PostMapping("/recipe/{recipeId}/image")
    public String uploadImage(@PathVariable String recipeId, @RequestParam("imagefile") MultipartFile file) {
        imageService.saveImage(Long.valueOf(recipeId), file);
        return "redirect:/recipe/"+recipeId+"/show";
    }
}
