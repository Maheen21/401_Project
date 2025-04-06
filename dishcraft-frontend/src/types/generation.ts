// src/types/generation.ts

export type RecipeGenerationRequest = {
    recipe: string;
    requiredIngredients: string[];
    availableIngredients: string[];
    description: string;
  };
  
  export type GeneratedRecipe = {
    recipe: string;
    missingIngredients: Record<string, string>;
    steps: string[];
  };
  