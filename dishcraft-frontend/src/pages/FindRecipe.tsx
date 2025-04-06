// src/pages/FindRecipe.tsx

import { useEffect, useState } from "react";
import IngredientCardGroup from "../components/IngredientCardGroup";
import ExploreFilterBar from "../components/ExploreFilterBar";
import RecipeCard from "../components/RecipeCard";

import {
  fetchAllIngredients,
  fetchFilteredIngredients,
  fetchDietaryRestrictions,
} from "../utils/ingredientApi";
import axios from "../utils/axiosInstance";

import type { Ingredient, DietaryRestriction, Recipe } from "../types";
import { groupByCategory } from "../utils/groupByCategory";

const FindRecipe = () => {
  const [ingredients, setIngredients] = useState<Ingredient[]>([]);
  const [selectedIds, setSelectedIds] = useState<number[]>([]);

  const [dietaryTags, setDietaryTags] = useState<DietaryRestriction[]>([]);
  const [excludedTags, setExcludedTags] = useState<string[]>([]);
  const [sortKey, setSortKey] = useState<"name" | "time">("name");

  const [searchMode, setSearchMode] = useState<"all" | "any">("all");
  const [foundRecipes, setFoundRecipes] = useState<Recipe[] | null>(null);
  const [searching, setSearching] = useState(false);
  const [error, setError] = useState("");

  // ✅ initial fetch of dietary restrictions and ingredients
  useEffect(() => {
    const loadInitial = async () => {
      const [drs, initialIngredients] = await Promise.all([
        fetchDietaryRestrictions(),
        fetchAllIngredients(),
      ]);
      setDietaryTags(drs);
      setIngredients(initialIngredients);
    };

    loadInitial();
  }, []);

  // ✅ filter ingredients based on dietary restrictions
  useEffect(() => {
    const updateIngredients = async () => {
      const ids = dietaryTags
        .filter((tag) => excludedTags.includes(tag.name))
        .map((tag) => tag.id);

      const data =
        ids.length === 0
          ? await fetchAllIngredients()
          : await fetchFilteredIngredients(ids);

      setIngredients(data);
    };

    updateIngredients();
  }, [excludedTags, dietaryTags]);

  const toggleSelect = (id: number) => {
    setSelectedIds((prev) =>
      prev.includes(id) ? prev.filter((x) => x !== id) : [...prev, id]
    );
  };

  const searchRecipes = async () => {
    setSearching(true);
    setError("");
    try {
      const params = new URLSearchParams();
      selectedIds.forEach((id) => params.append("ingredientIds", id.toString()));
      params.append("mode", searchMode);

      const res = await axios.get<Recipe[]>(
        `/api/recipes/search?${params.toString()}`
      );
      setFoundRecipes(res.data);
    } catch {
      setError("Recipe search failed.");
    } finally {
      setSearching(false);
    }
  };

  const grouped = groupByCategory(ingredients);

  return (
    <div className="p-8 max-w-screen-xl mx-auto">
      <h1 className="text-3xl font-bold mb-8">🥣 Find a Recipe</h1>

      {/* Filter bar */}
      <ExploreFilterBar
        sortKey={sortKey}
        setSortKey={setSortKey}
        excludedTags={excludedTags}
        setExcludedTags={setExcludedTags}
        tagOptions={dietaryTags}
      />

      {/* search and button */}
      <div className="flex items-center gap-4 mb-6">
        <select
          value={searchMode}
          onChange={(e) => setSearchMode(e.target.value as "all" | "any")}
          className="border px-3 py-1 rounded"
        >
          <option value="all">Must include all selected ingredients</option>
          <option value="any">Include any selected ingredients</option>
        </select>

        <button
          onClick={searchRecipes}
          disabled={selectedIds.length === 0 || searching}
          className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 disabled:opacity-50"
        >
          {searching ? "Searching..." : "Search Recipe"}
        </button>
      </div>
            {/* search result */}
            {foundRecipes && (
        <div className="mt-12">
          <h2 className="text-2xl font-bold mb-4">🔍 Results</h2>
          {foundRecipes.length === 0 ? (
            <p className="text-gray-600">No recipes found with the selected ingredients.</p>
          ) : (
            <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
              {foundRecipes.map((recipe) => (
                <RecipeCard key={recipe.id} recipe={recipe} />
              ))}
            </div>
          )}
        </div>
      )}

      {/* ingredients group */}
      <div className="space-y-8">
        {Object.entries(grouped).map(([category, items]) => (
          <IngredientCardGroup
            key={category}
            title={category}
            ingredients={items}
            selectedIds={selectedIds}
            toggleSelect={toggleSelect}
          />
        ))}
      </div>



      {error && <p className="text-red-500 mt-4">{error}</p>}
    </div>
  );
};

export default FindRecipe;
