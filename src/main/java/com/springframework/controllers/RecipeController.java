package com.springframework.controllers;

import com.springframework.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RecipeController {
    RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("/recipe/show/{id}")
    public String showById(@PathVariable String id, Model model){

        model.addAttribute("recipe",recipeService.findById(Long.valueOf(id)));
        return "recipe/show";
    }

/*    @GetMapping("/image/recipe{id}")
    public void showImage(@PathVariable String id, HttpServletResponse response) throws IOException {
        response.setContentType("image/jpeg");

        Recipe recipe = recipeService.findById(Long.valueOf(id));
        Byte[] byteImg = recipe.getImage();
        byte[] resimg = new byte[byteImg.length];
        for (int i=0; i< resimg.length; i++)
            resimg[i] = byteImg[i];
        InputStream is = new ByteArrayInputStream(resimg);
        IOUtils.copy(is, response.getOutputStream());
    }
*/

}
