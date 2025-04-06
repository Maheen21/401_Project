import { useEffect, useState } from "react";
import IngredientGroup from "../components/IngredientGroup";
import ExploreFilterBar from "../components/ExploreFilterBar";
import {
  fetchAllIngredients,
  fetchFilteredIngredients,
  fetchDietaryRestrictions,
} from "../utils/ingredientApi";

import type { Ingredient, DietaryRestriction } from "../types";
import { groupByCategory } from "../utils/groupByCategory";

const FindRecipe = () => {
  const [ingredients, setIngredients] = useState<Ingredient[]>([]);
  const [selectedIds, setSelectedIds] = useState<number[]>([]);

  const [dietaryTags, setDietaryTags] = useState<DietaryRestriction[]>([]);
  const [excludedTags, setExcludedTags] = useState<string[]>([]);

  const [sortKey, setSortKey] = useState<"name" | "time">("name");

  // Initial load of DR and ingredients
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

  // Reload ingredients on excluded DR tag change
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

  const grouped = groupByCategory(ingredients);

  return (
    <div className="p-8 max-w-screen-xl mx-auto">
      <h1 className="text-3xl font-bold mb-8">🥣 Find a Recipe</h1>

      <ExploreFilterBar
        sortKey={sortKey}
        setSortKey={setSortKey}
        excludedTags={excludedTags}
        setExcludedTags={setExcludedTags}
        tagOptions={dietaryTags}
      />

      <div className="space-y-8">
        {Object.entries(grouped).map(([category, items]) => (
          <IngredientGroup
            key={category}
            title={category}
            ingredients={items}
            selectedIds={selectedIds}
            onToggle={toggleSelect}
          />
        ))}
      </div>
    </div>
  );
};

export default FindRecipe;
