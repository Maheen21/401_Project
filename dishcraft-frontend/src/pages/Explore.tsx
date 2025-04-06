// src/pages/Explore.tsx

import { useEffect, useState } from "react";
import ExploreFilterBar from "../components/ExploreFilterBar";
import RecipeCard from "../components/RecipeCard";
import { DietaryRestriction, Recipe } from "../types";
import { getAllRecipes, getFilteredRecipes, getAllDietaryRestrictions } from "../utils/recipeApi";

const Explore = () => {
  const [recipes, setRecipes] = useState<Recipe[]>([]);
  const [allTags, setAllTags] = useState<DietaryRestriction[]>([]);
  const [excludedTags, setExcludedTags] = useState<string[]>([]);
  const [sortKey, setSortKey] = useState<"name" | "time">("name");
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string>("");

  // Fetch dietary restriction tag list
  useEffect(() => {
    const fetchTags = async () => {
      try {
        const tags = await getAllDietaryRestrictions();
        setAllTags(tags);
      } catch (err) {
        console.error("Failed to fetch dietary restriction tags:", err);
      }
    };
    fetchTags();
  }, []);

  // Fetch recipes
  useEffect(() => {
    const fetchRecipes = async () => {
      setLoading(true);
      setError("");

      try {
        if (excludedTags.length === 0) {
          const data = await getAllRecipes();
          setRecipes(data);
        } else {
          const excludedIds = allTags
            .filter((tag) => excludedTags.includes(tag.name))
            .map((tag) => tag.id);
          const data = await getFilteredRecipes(excludedIds);
          setRecipes(data);
        }
      } catch (err) {
        console.error("Failed to fetch recipes:", err);
        setError("Failed to load recipes.");
      } finally {
        setLoading(false);
      }
    };

    fetchRecipes();
  }, [excludedTags, allTags]);

  // Sort frontend
  const sortedRecipes = [...recipes].sort((a, b) => {
    if (sortKey === "time") return a.cookingTime - b.cookingTime;
    return a.name.localeCompare(b.name);
  });

  return (
    <div className="p-10">
      <h1 className="text-3xl font-bold mb-8">🍽️ Explore Recipes</h1>

      <ExploreFilterBar
        sortKey={sortKey}
        setSortKey={setSortKey}
        excludedTags={excludedTags}
        setExcludedTags={setExcludedTags}
        tagOptions={allTags}
      />

      {loading && <p>Loading recipes...</p>}
      {error && <p className="text-red-500">{error}</p>}

      <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
        {sortedRecipes.map((recipe) => (
          <RecipeCard key={recipe.id} recipe={recipe} />
        ))}
      </div>
    </div>
  );
};

export default Explore;
