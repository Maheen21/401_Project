import { useState } from "react";
import IngredientCard from "../components/IngredientCard";
import IngredientCardGroup from "../components/IngredientCardGroup"; // import

const TestComponents = () => {
  const [selectedIds, setSelectedIds] = useState<number[]>([]);

  const ingredients = [
    {
      id: 1,
      label: "spaghetti",
      description: "Italian pasta made from wheat.",
      tags: ["Grain"],
      violations: ["Gluten"],
      rank: "MAIN"as const,
      category: "Supporting Ingredients"as const,
    },
    {
      id: 2,
      label: "pancetta",
      description: "Cured pork belly.",
      tags: ["Meat"],
      violations: ["Halal"],
      rank: "MAIN"as const,
      category: "Main Protein"as const,
    },
    {
      id: 3,
      label: "egg yolks",
      description: "Yolks from chicken eggs.",
      tags: ["Protein"],
      violations: ["Vegetarian"],
      rank: "MAIN"as const,
      category: "Main Protein"as const,
    },
    {
      id: 4,
      label: "chicken breast",
      description: "Boneless chicken breast.",
      tags: ["Meat"],
      violations: ["Vegan"],
      rank: "MAIN"as const,
      category: "Main Protein"as const,
    },
  ];

  const toggleSelect = (id: number) => {
    setSelectedIds((prev) =>
      prev.includes(id) ? prev.filter((i) => i !== id) : [...prev, id]
    );
  };

  return (
    <div className="p-8 space-y-6 max-w-screen-lg mx-auto">
      <h1 className="text-3xl font-bold flex items-center gap-2">
        🧪 <span>IngredientCard Test</span>
      </h1>

      {/* individual card test */}
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
        {ingredients.map((ing) => (
          <IngredientCard
            key={ing.id}
            label={ing.label}
            description={ing.description}
            tags={ing.tags}
            violations={ing.violations}
            rank={ing.rank as "MAIN" | "SUB"}
            selected={selectedIds.includes(ing.id)}
            onClick={() => toggleSelect(ing.id)}
          />
        ))}
      </div>

      {/* grouped card test */}
      <div className="mt-12">
        <h2 className="text-2xl font-semibold mb-4">🧃 Group Component Test</h2>
        <IngredientCardGroup
          title="Main Protein Group"
          ingredients={ingredients}
          selectedIds={selectedIds}
          toggleSelect={toggleSelect}
        />
      </div>
    </div>
  );
};

export default TestComponents;
