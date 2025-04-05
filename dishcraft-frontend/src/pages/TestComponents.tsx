import { useState } from "react";
import IngredientCard from "../components/IngredientCard";

const TestComponents = () => {
  const [selectedIds, setSelectedIds] = useState<number[]>([]);

  // 예시용 데이터 (각 ingredient에 id 부여)
  const ingredients = [
    {
      id: 1,
      label: "spaghetti",
      description: "Italian pasta made from wheat.",
      tags: ["Grain", "Gluten"],
    },
    {
      id: 2,
      label: "pancetta",
      description: "Cured pork belly.",
      tags: ["Meat", "Non-Halal"],
    },
    {
      id: 3,
      label: "egg yolks",
      description: "Yolks from chicken eggs.",
      tags: ["Protein", "Vegetarian"],
    },
  ];

  const toggleSelect = (id: number) => {
    setSelectedIds((prev) =>
      prev.includes(id) ? prev.filter((i) => i !== id) : [...prev, id]
    );
  };

  return (
    <div className="p-8 space-y-6 max-w-3xl mx-auto">
      <h1 className="text-2xl font-bold">🧪 IngredientCard Test</h1>

      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
        {ingredients.map((ing) => (
          <IngredientCard
            key={ing.id}
            label={ing.label}
            description={ing.description}
            tags={ing.tags}
            selected={selectedIds.includes(ing.id)}
            onClick={() => toggleSelect(ing.id)}
          />
        ))}
      </div>
    </div>
  );
};

export default TestComponents;
