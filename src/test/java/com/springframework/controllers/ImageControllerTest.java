package com.springframework.controllers;

import com.springframework.commands.RecipeCommand;
import com.springframework.services.ImageService;
import com.springframework.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ImageControllerTest {

    @Mock
    ImageService imageService;
    @Mock
    RecipeService recipeService;
    
    ImageController controller;

    MockMvc mockMvc;
    
    @BeforeEach
    void setUp() {
        controller = new ImageController(imageService, recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getImageForm() throws Exception {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

        //when
        mockMvc.perform(get("/recipe/1/image"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/imageuploadform"))
                .andExpect(model().attributeExists("recipe"));

        //then
        verify(recipeService).findCommandById(anyLong());
    }

    @Test
    void uploadImage() throws Exception{
        MockMultipartFile multipartFile =
                new MockMultipartFile("imagefile", "testing.txt", "text/plain",
                        "recipe project".getBytes());

        mockMvc.perform(multipart("/recipe/1/image").file(multipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location","/recipe/1/show"));

        verify(imageService).saveImage(anyLong(),any());
    }
}