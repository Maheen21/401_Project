export type IngredientRank = "MAIN" | "SUB";

export type IngredientCategory =
  | "Main Protein"
  | "Supporting Ingredients"
  | "Spices & Sauces";

export type Ingredient = {
  id: number;
  label: string;
  description?: string;
  tags?: string[];
  violations?: string[];
  rank?: IngredientRank;
  category: IngredientCategory;
};