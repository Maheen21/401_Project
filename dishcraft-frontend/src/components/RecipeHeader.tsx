import type { Recipe } from "../types";

interface RecipeHeaderProps {
    recipe: Recipe;
  }
  
  const RecipeHeader = ({ recipe }: RecipeHeaderProps) => (
    <div className="flex flex-col lg:flex-row gap-6">
      <img
        src={recipe.imageUrl}
        alt={recipe.name}
        className="w-full lg:w-1/2 h-64 object-cover rounded-md"
      />
      <div className="flex-1">
        <h1 className="text-3xl font-bold mb-2">{recipe.name}</h1>
        <p className="text-gray-700 mb-4">{recipe.description}</p>
        <p className="text-sm text-gray-500">⏱️ Cooking Time: {recipe.cookingTime} minutes</p>
      </div>
    </div>
  );
  
  export default RecipeHeader;