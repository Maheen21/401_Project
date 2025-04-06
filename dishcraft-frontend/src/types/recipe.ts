export type RecipeIngredient = {
    ingredientId: number;
    name: string;
    quantity: number;
    unit: string;
    isRequired: boolean;
  };
  
export type Recipe = {
    id: number;
    name: string;
    description: string;
    cookingTime: number;
    imageUrl: string;
    dietaryRestrictions: { id: number; name: string }[];
    recipeIngredients: RecipeIngredient[];
  };