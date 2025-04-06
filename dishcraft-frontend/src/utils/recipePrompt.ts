// src/utils/recipePrompt.ts


import type { RecipeGenerationRequest } from "../types";

export const generateRecipePrompt = (input: RecipeGenerationRequest): string => {
  const { recipe, requiredIngredients, availableIngredients, description } = input;

  return `
Recipe Name: ${recipe}

Required Ingredients:
${requiredIngredients.map((i) => `- ${i}`).join("\n")}

Available Ingredients:
${availableIngredients.map((i) => `- ${i}`).join("\n")}

Description:
${description}
  `.trim();
};