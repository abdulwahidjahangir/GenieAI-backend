# Genie AI

Genie AI is an AI-powered assistant that can generate recipes, answer questions, and create stunning images.

## Features

- Generate recipes
- Answer questions
- Create stunning images

## Endpoints

### Generate image    
- Endpoint: `/generate-image`    
- Method: POST    
- Request body: `{ "prompt": "Your image prompt" }`    
- Params: `prompt - required`, `width - optional`, `height - optional`, `quality - optional`, `n - optional`
- Response: `["https://example.com/image1.jpg", "https://example.com/image2.jpg"]`

### Generate recipe

- Endpoint: `/generate-recipe`
- Method: POST
- Request body: `{ "prompt": "Your recipe prompt" }`
- Params: `ingredients - required`, `cuisine - optional`, `dietaryRestrictions - optional`
- Response: `"Your generated recipe"`

### Answer question

- Endpoint: `/answer-question`
- Method: POST
- Request body: `{ "prompt": "Your question prompt" }`
- Response: `"Your answer to the question"`
