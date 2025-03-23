package com.wahid.GenieAI.services;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RecipeService {

    private final ChatModel chatModel;

    @Autowired
    public RecipeService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }


    public String createRecipe(String ingredients, String cuisine, String dietaryRestriction) {

        String template = """
                    I want to create a recipe using hte following ingredients: {ingredients}.
                    The cuisine type I prefer is {cuisine}.
                    Please consider the following dietary restrictions: {dietaryRestriction}.
                    Please provide me with a detailed recipe including title, list of ingredients, and cooking instructions.
                """;

        PromptTemplate promptTemplate = new PromptTemplate(template);
        Map<String, Object> params = Map.of(
                "ingredients", ingredients,
                "cuisine", cuisine,
                "dietaryRestriction", dietaryRestriction
        );

        Prompt prompt = promptTemplate.create(params);

        String recipe = chatModel.call(prompt).getResult().getOutput().getText();

        return recipe;
    }
}
