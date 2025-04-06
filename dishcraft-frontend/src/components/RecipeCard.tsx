import { Link } from "react-router-dom";
import type { Recipe } from "../types";
import ViolationTag from "./ViolationTag";

interface RecipeCardProps {
  recipe: Recipe;
}

const RecipeCard = ({ recipe }: RecipeCardProps) => {
  return (
    <Link to={`/recipes/${recipe.id}`} className="block">
      <div className="border rounded-lg p-4 flex flex-col gap-3 w-full bg-white shadow-sm hover:shadow-md hover:scale-[1.02] transition-transform duration-200 cursor-pointer">
        <img
          src={recipe.imageUrl}
          alt={recipe.name}
          className="h-40 w-full object-cover rounded-md"
        />
        <div className="flex flex-col gap-1">
          <h2 className="font-semibold text-lg">{recipe.name}</h2>
          <p className="text-sm text-gray-500">{recipe.cookingTime} min</p>
          <p className="text-sm text-gray-600">{recipe.description}</p>
        </div>
        {recipe.dietaryRestrictions.length > 0 && (
          <div className="flex flex-wrap gap-2 pt-2">
            {recipe.dietaryRestrictions.map((tag) => (
              <ViolationTag key={tag.id} label={tag.name} />
            ))}
          </div>
        )}
      </div>
    </Link>
  );
};

export default RecipeCard;
