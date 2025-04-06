import { Ingredient } from "../types";

export const groupByCategory = (ingredients: Ingredient[]) => {
  const groups: Record<string, Ingredient[]> = {};

  for (const ing of ingredients) {
    if (!groups[ing.category]) {
      groups[ing.category] = [];
    }
    groups[ing.category].push(ing);
  }

  return groups;
};