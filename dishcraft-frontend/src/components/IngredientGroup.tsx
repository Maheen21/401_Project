import IngredientCard from "./IngredientCard";

import type { Ingredient } from "../types";

interface IngredientGroupProps {
  title: string;
  ingredients: Ingredient[];
  selectedIds: number[];
  onToggle: (id: number) => void;
}

const IngredientGroup = ({
  title,
  ingredients,
  selectedIds,
  onToggle,
}: IngredientGroupProps) => {
  return (
    <div className="mb-8">
      <h2 className="text-xl font-semibold mb-3">{title}</h2>
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
        {ingredients.map((ing) => (
          <IngredientCard
            key={ing.id}
            label={ing.label}
            description={ing.description}
            tags={ing.tags}
            rank={ing.rank}
            selected={selectedIds.includes(ing.id)}
            onClick={() => onToggle(ing.id)}
          />
        ))}
      </div>
    </div>
  );
};

export default IngredientGroup;
