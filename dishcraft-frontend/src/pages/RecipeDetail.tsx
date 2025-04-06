import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { startRecipeGeneration } from "../utils/llmApi";
import type { Recipe, Ingredient, GeneratedRecipe, DietaryRestriction  } from "../types";
import { fetchRecipeById } from "../utils/recipeApi";
import { fetchAllIngredients } from "../utils/ingredientApi";
import { generateRecipePrompt } from "../utils/recipePrompt";
import { groupByCategory } from "../utils/groupByCategory";
import RecipeDietaryInfo from "../components/RecipeDietaryInfo";
import RecipeHeader from "../components/RecipeHeader";
import RecipeIngredientSection from "../components/RecipeIngredientSection";
import GeneratedRecipeSection from "../components/GeneratedRecipeSection";
import { fetchDietaryRestrictions } from "../utils/ingredientApi";
import FavoriteButtonCard from "../components/FavoriteButtonCard";

const RecipeDetail = () => {
  const { id } = useParams<{ id: string }>();

  const [recipe, setRecipe] = useState<Recipe | null>(null);
  const [ingredients, setIngredients] = useState<Ingredient[]>([]);
  const [selectedIds, setSelectedIds] = useState<number[]>([]);
  const [generated, setGenerated] = useState<GeneratedRecipe | null>(null);
  const [loading, setLoading] = useState(false);
  const [allTags, setAllTags] = useState<DietaryRestriction[]>([]);
  const [isFavorite, setIsFavorite] = useState(false);

  // Fetch recipe + all ingredients on mount
  useEffect(() => {
    const load = async () => {
        const [recipeData, allIngredients, tags] = await Promise.all([
            fetchRecipeById(Number(id)),
            fetchAllIngredients(),
            fetchDietaryRestrictions(),
          ]);
          setRecipe(recipeData);
          setIngredients(allIngredients);
          setAllTags(tags);
        };
      

    load();
  }, [id]);

  const toggleSelect = (id: number) => {
    setSelectedIds((prev) =>
      prev.includes(id) ? prev.filter((x) => x !== id) : [...prev, id]
    );
  };

  const handleGenerate = async () => {
    if (!recipe) return;
  
    setLoading(true);
  
    const requiredIngredientIds = recipe.recipeIngredients.map((ri) => ri.ingredientId);
    const requiredLabels = ingredients
      .filter((ing) => requiredIngredientIds.includes(ing.id))
      .map((i) => i.label);
  
    const available = ingredients
      .filter((i) => selectedIds.includes(i.id))
      .map((i) => i.label);
  
    const promptInput = {
      recipe: recipe.name,
      requiredIngredients: requiredLabels,
      availableIngredients: available,
      description: recipe.description,
    };
  
    const result = await startRecipeGeneration(promptInput);
    if (result) setGenerated(result);
  
    setLoading(false);
  };

  if (!recipe) return <p>Loading recipe...</p>;

  // only show ingredients that are part of the recipe
  // and are selected by the user
  const requiredIngredientIds = recipe.recipeIngredients.map((ri) => ri.ingredientId);
  const filteredIngredients = ingredients.filter((ing) =>
    requiredIngredientIds.includes(ing.id)
  );

  const grouped = groupByCategory(filteredIngredients);

  return (
    <div className="p-6 max-w-screen-xl mx-auto">
        <div className="flex items-center gap-4 mb-6">
      <RecipeHeader recipe={recipe} />
        </div>

      
        <div className="flex items-center gap-4 mb-6">
        <FavoriteButtonCard
          recipeId={recipe.id}
        />
        </div>
      <RecipeDietaryInfo allTags={allTags} violatedTags={recipe.dietaryRestrictions} />

      <RecipeIngredientSection
        ingredients={filteredIngredients}
        selectedIds={selectedIds}
        onToggle={toggleSelect}
      />

      <div className="mt-6 text-right">
        <button
          onClick={handleGenerate}
          disabled={loading}
          className="px-4 py-2 bg-blue-600 text-white rounded-md shadow hover:bg-blue-700"
        >
          {loading ? "Generating..." : "Generate Instructions"}
        </button>
      </div>

      {generated && <GeneratedRecipeSection result={generated} />}
    </div>
  );
};

export default RecipeDetail;
