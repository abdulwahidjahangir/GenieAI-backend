package com.wahid.GenieAI.controllers;

import com.wahid.GenieAI.services.ChatService;
import com.wahid.GenieAI.services.ImageService;
import com.wahid.GenieAI.services.RecipeService;
import org.springframework.ai.image.ImageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class GenieAIController {

    private final ChatService chatService;
    private final ImageService imageService;
    private final RecipeService recipeService;

    @Autowired
    public GenieAIController(ChatService chatService, ImageService imageService, RecipeService recipeService) {
        this.chatService = chatService;
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("/ask-ai")
    public String getResponse(@RequestParam String prompt) throws IOException {
        prompt = prompt.strip();
        if (prompt.isEmpty()) {
            return "Invalid input. Please a provide a valid prompt";
        }

        return chatService.getResponseOptions(prompt);
    }

    @GetMapping("/generate-image")
    public ResponseEntity<?> generateImage(
            @RequestParam String prompt,
            @RequestParam(value = "quality", defaultValue = "hd") String quality,
            @RequestParam(value = "n", defaultValue = "1") int n,
            @RequestParam(value = "width", defaultValue = "1024") int width,
            @RequestParam(value = "height", defaultValue = "1024") int height
    ) throws IOException {
        prompt = prompt.strip();

        if (prompt.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid input. Please provide a valid prompt");
        }

        ImageResponse imageResponse = imageService.generateImage(prompt, quality, n, width, height);

        List<String> imageUrls = imageResponse.getResults().stream()
                .map(result -> result.getOutput().getUrl())
                .toList();

        return ResponseEntity.ok(imageUrls);
    }

    @GetMapping("/generate-recipe")
    public ResponseEntity<?> recipeCreator(
            @RequestParam String ingredients,
            @RequestParam(defaultValue = "any") String cuisine,
            @RequestParam(defaultValue = "") String dietaryRestrictions
    ) throws IOException {
        if(ingredients.isBlank()){
            return ResponseEntity.badRequest().body("Invalid ingredients. Please provide valid ingredients");
        }

        String recipe = recipeService.createRecipe(ingredients, cuisine, dietaryRestrictions);

        return ResponseEntity.ok(recipe);
    }
}
