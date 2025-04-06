// src/utils/normalize.ts

import type { Ingredient, IngredientCategory, IngredientRank } from "../types";

const allowedRanks = ["MAIN", "SUB"] as const;
const allowedCategories = [
  "Main Protein",
  "Supporting Ingredients",
  "Spices & Sauces",
] as const;

export function normalizeIngredient(raw: any): Ingredient | null {
  const {
    id,
    name, // ✅ backend uses "name" instead of "label"
    description,
    category,
    rank,
    dietaryRestrictions, // ✅ backend uses "dietaryRestrictions" instead of "violations"
  } = raw;

  if (
    !allowedRanks.includes(rank) ||
    !allowedCategories.includes(category)
  ) {
    console.warn("Invalid ingredient data:", raw);
    return null;
  }

  return {
    id,
    label: name,
    description,
    category: category as IngredientCategory,
    rank: rank as IngredientRank,
    tags: [], // TODO: Add tags if available in the backend response
    violations: dietaryRestrictions?.map((d: any) => d.name) || [],
  };
}
