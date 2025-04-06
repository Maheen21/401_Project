import IngredientCard from "./IngredientCard";

import type { Ingredient } from "../types";

interface IngredientCardGroupProps {
  title: string;
  ingredients: Ingredient[];
  selectedIds: number[];
  toggleSelect: (id: number) => void;
}

const IngredientCardGroup = ({
  title,
  ingredients,
  selectedIds,
  toggleSelect,
}: IngredientCardGroupProps) => {
  return (
    <section className="mb-8">
      <h2 className="text-xl font-semibold mb-4">{title}</h2>
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
        {ingredients.map((ing) => (
          <IngredientCard
            key={ing.id}
            label={ing.label}
            description={ing.description}
            tags={ing.tags}
            violations={ing.violations}
            rank={ing.rank}
            selected={selectedIds.includes(ing.id)}
            onClick={() => toggleSelect(ing.id)}
          />
        ))}
      </div>
    </section>
  );
};

export default IngredientCardGroup;
