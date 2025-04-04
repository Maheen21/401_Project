import { useEffect, useState } from "react";
import axios from "../utils/axiosInstance";
import RecipeCard from "../components/RecipeCard";
import ExploreFilterBar from "../components/ExploreFilterBar";

// Types
interface Recipe {
  id: number;
  name: string;
  description: string;
  cookingTime: number;
  imageUrl: string;
  dietaryRestrictions: { id: number; name: string }[];
}

interface Tag {
  id: number;
  name: string;
}

const Explore = () => {
  const [recipes, setRecipes] = useState<Recipe[]>([]);
  const [sortKey, setSortKey] = useState<"name" | "time">("name");

  const [excludedTags, setExcludedTags] = useState<string[]>([]);
  const [allTags, setAllTags] = useState<Tag[]>([]);

  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  // Fetch dietary restriction tag list
  useEffect(() => {
    const fetchTags = async () => {
      try {
        const res = await axios.get<Tag[]>("/api/dietary-restrictions");
        setAllTags(res.data);
      } catch {
        console.error("Failed to fetch dietary restriction tags.");
      }
    };
    fetchTags();
  }, []);

  // Fetch recipes when excludedTags change
  useEffect(() => {
    const fetchRecipes = async () => {
      setLoading(true);
      try {
        if (excludedTags.length === 0) {
          // Default: fetch all recipes
          const res = await axios.get<Recipe[]>("/api/recipes/all");
          setRecipes(res.data);
        } else {
          // Filtered: convert tag names to IDs
          const ids = allTags
            .filter((tag) => excludedTags.includes(tag.name))
            .map((tag) => tag.id);

          const params = new URLSearchParams();
          ids.forEach((id) => params.append("dietaryRestrictionIds", id.toString()));

          const res = await axios.get<Recipe[]>(
            `/api/recipes/filter/dietary?${params.toString()}`
          );
          setRecipes(res.data);
        }
      } catch {
        setError("Failed to load recipes.");
      } finally {
        setLoading(false);
      }
    };

    fetchRecipes();
  }, [excludedTags, allTags]);

  // Optional frontend sort
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
        tagOptions={allTags.map((t) => t.name)}
      />

      {loading && <p>Loading recipes...</p>}
      {error && <p className="text-red-500">{error}</p>}

      <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
        {sortedRecipes.map((recipe) => (
          <RecipeCard
            key={recipe.id}
            title={recipe.name}
            time={`${recipe.cookingTime} min`}
            description={recipe.description}
            imageUrl={recipe.imageUrl}
            tags={recipe.dietaryRestrictions.map((d) => d.name)}
          />
        ))}
      </div>
    </div>
  );
};

export default Explore;
