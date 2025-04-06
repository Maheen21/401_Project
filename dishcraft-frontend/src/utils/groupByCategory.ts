// src/utils/groupByCategory.ts

import type { Ingredient, IngredientCategory } from "../types";

const CATEGORY_ORDER: IngredientCategory[] = [
  "Main Protein",
  "Supporting Ingredients",
  "Spices & Sauces",
];

export const groupByCategory = (ingredients: Ingredient[]) => {
  const groups: Record<IngredientCategory, Ingredient[]> = {
    "Main Protein": [],
    "Supporting Ingredients": [],
    "Spices & Sauces": [],
  };

  for (const ing of ingredients) {
    groups[ing.category].push(ing);
  }

  return CATEGORY_ORDER.reduce((sorted, category) => {
    if (groups[category].length > 0) {
      sorted[category] = groups[category];
    }
    return sorted;
  }, {} as Record<string, Ingredient[]>);
};
